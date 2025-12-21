package dev.alexissdev.storage.redis.channel;

public interface ChannelListener<T> {

    /**
     * Listens for messages on a specific channel and server. The provided message payload
     * object is used to process incoming messages.
     *
     * @param channel the communication channel on which to listen for messages
     * @param server  the identifier of the server from which messages will be received
     * @param object  the message payload object to handle or filter incoming messages
     */

    void listen(Channel<T> channel, String server, T object);

    /**
     * Sends a message to the specified communication channel.
     *
     * @param channel the communication channel to which the message will be sent
     * @param object  the message payload to be sent through the channel
     */

    default void send(Channel<T> channel, T object) { }
}