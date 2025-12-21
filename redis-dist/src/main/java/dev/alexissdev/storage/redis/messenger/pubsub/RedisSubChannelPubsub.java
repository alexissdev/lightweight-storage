package dev.alexissdev.storage.redis.messenger.pubsub;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.alexissdev.storage.redis.channel.RedisChannel;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;

/**
 * A specialized implementation of {@code JedisPubSub} that listens to Redis Pub/Sub messages
 * on a parent channel and routes them to the appropriate {@code RedisChannel} instances. This
 * class is designed to handle messages exchanged between servers in a distributed system
 * and to target specific servers if needed.
 *
 * Responsibilities:
 * - Listens for Redis Pub/Sub messages on a specified parent channel.
 * - Filters messages based on the originating and target server IDs.
 * - Delegates message handling to the appropriate {@code RedisChannel} instance, if available.
 *
 * Features:
 * - Ensures that messages are processed only if they originate from a different server.
 * - Supports targeted message delivery to a specific server ID.
 * - Deserializes incoming messages using Gson and invokes listeners on the corresponding channel.
 *
 *
 * Methods:
 * - {@code onMessage(String channel, String message)}: Handles incoming messages on subscribed channels,
 *   filters and processes them, and delegates message handling to the appropriate channel.
 */

public class RedisSubChannelPubsub
        extends JedisPubSub {

    private final String parentChannel;
    private final String serverId;
    private final Gson gson;
    private final Map<String, RedisChannel<?>> channels;

    public RedisSubChannelPubsub(
            String parentChannel, String serverId,
            Gson gson,
            Map<String, RedisChannel<?>> channels
    ) {
        this.parentChannel = parentChannel;
        this.serverId = serverId;
        this.gson = gson;
        this.channels = channels;
    }

    @Override
    public void onMessage(String channel, String message) {
        // we don't care if the message isn't from the parent channel
        if (!channel.equals(parentChannel)) {
            return;
        }

        // we can parse the message as a JSON object
        JsonObject jsonMessage = JsonParser.parseString(message)
                .getAsJsonObject();

        String serverId = jsonMessage.get("server")
                .getAsString();

        // if the message is from the server we're listening to
        if (serverId.equals(this.serverId)) {
            return;
        }

        JsonElement targetServerElement = jsonMessage.get("targetServer");

        if (targetServerElement != null) {
            String targetServer = targetServerElement.getAsString();

            // if the message isn't for this server, ignore it
            if (!targetServer.equals(this.serverId)) {
                return;
            }
        }

        String subChannel = jsonMessage.get("channel")
                .getAsString();

        @SuppressWarnings("unchecked")
        RedisChannel<Object> channelObject =
                (RedisChannel<Object>) channels.get(subChannel);

        // if the channel doesn't exist, we can't do anything
        if (channelObject == null) {
            return;
        }

        JsonElement object = jsonMessage.get("object");
        Object deserializedObject = gson.fromJson(
                object,
                channelObject.getType()
                        .getType()
        );

        channelObject.listen(serverId, deserializedObject);
    }
}
