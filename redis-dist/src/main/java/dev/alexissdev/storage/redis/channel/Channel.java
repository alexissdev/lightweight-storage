package dev.alexissdev.storage.redis.channel;

import com.google.gson.reflect.TypeToken;
import dev.alexissdev.storage.redis.messenger.Messenger;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Represents a communication channel that facilitates message exchange between different servers or components.
 * The channel is associated with a specific type of message payload and allows sending and listening
 * to messages as well as registering listeners.
 *
 * @param <T> the type of message payload that the channel handles
 */

public interface Channel<T> {

    /**
     * Retrieves the name of the channel.
     *
     * @return the name of the channel as a string
     */

    String getName();

    /**
     * Retrieves the Messenger instance associated with the channel.
     *
     * @return the Messenger instance used for obtaining or managing communication channels
     */

    Messenger getMessenger();

    /**
     * Retrieves the {@code TypeToken} representing the type of payload that this channel handles.
     *
     * @return the {@code TypeToken} of the message payload type
     */

    TypeToken<T> getType();

    /**
     * Sends a message to the communication channel. Optionally, the message can
     * be directed to a specified target server. If the target server is {@code null},
     * the message will be broadcast to all available servers.
     *
     * @param message      the message payload to be sent through the channel
     * @param targetServer the identifier of the target server to which the message should
     *                     be addressed; if {@code null}, the message will be broadcast
     *                     to all servers
     * @return the instance of this channel to allow method chaining
     */

    Channel<T> sendMessage(T message, @Nullable String targetServer);

    default Channel<T> sendMessage(T message) {
        return sendMessage(message, null);
    }

    /**
     * Adds a listener to the channel, allowing it to handle incoming messages.
     * The specified {@code ChannelListener} will be notified when messages
     * are received on this channel.
     *
     * @param channelListener the listener to be added to the channel. It will
     *                        process messages of the type handled by this channel.
     * @return the current instance of the channel to allow method chaining.
     */

    Channel<T> addListener(ChannelListener<T> channelListener);

    /**
     * Starts listening for messages on the specified server for the current channel.
     * This method allows the channel to handle messages sent by the given server
     * and processes them using the associated payload type.
     *
     * @param server the identifier of the server on which the channel should start listening
     * @param object the message payload object that acts as a filter or handler
     *               during the listening process
     */

    void listen(String server, T object);

    /**
     * Retrieves the set of listeners currently registered to the channel.
     * These listeners will be notified when messages are received on the channel.
     *
     * @return a set of {@code ChannelListener} instances associated with the channel
     */

    Set<ChannelListener<T>> getListeners();
}