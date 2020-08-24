package mineverse.Aust1n46.chat.channel;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import mineverse.Aust1n46.chat.MineverseChat;
import mineverse.Aust1n46.chat.utilities.Format;

/**
 * Chat channel object pojo. Class also contains static initialization methods
 * for reading chat channels from the config file.
 * 
 * @author Aust1n46
 */
public class ChatChannel {
	private static final String PERMISSION_PREFIX = "venturechat.";
	private static final String NO_PERMISSIONS = "venturechat.none";

	private static MineverseChat plugin = MineverseChat.getInstance();
	private static ChatChannel defaultChatChannel;
	private static ChatChannel[] channels;
	private static String defaultColor;

	private String name;
	private String permission;
	private String speakPermission;
	private boolean mutable;
	private String color;
	private String chatColor;
	private boolean defaultChannel;
	private boolean autojoin;
	private String alias;
	private double distance;
	private boolean filter;
	private boolean bungee;
	private String format;
	private int cooldown;

	/**
	 * Read chat channels from config file and initialize channel array.
	 */
	public static void initialize() {
		ConfigurationSection cs = plugin.getConfig().getConfigurationSection("channels");
		int len = (cs.getKeys(false)).size();
		channels = new ChatChannel[len];
		int counter = 0;
		for (String key : cs.getKeys(false)) {
			String color = cs.getString(key + ".color", "white");
			String chatColor = cs.getString(key + ".chatcolor", "white");
			String name = key;
			String permission = cs.getString(key + ".permissions", "None");
			String speakPermission = cs.getString(key + ".speak_permissions", "None");
			boolean mutable = cs.getBoolean(key + ".mutable", false);
			boolean filter = cs.getBoolean(key + ".filter", true);
			boolean bungee = cs.getBoolean(key + ".bungeecord", false);
			String format = cs.getString(key + ".format", "Default");
			boolean defaultChannel = cs.getBoolean(key + ".default", false);
			String alias = cs.getString(key + ".alias", "None");
			double distance = cs.getDouble(key + ".distance", (double) 0);
			int cooldown = cs.getInt(key + ".cooldown", 0);
			boolean autojoin = cs.getBoolean(key + ".autojoin", false);
			ChatChannel chatChannel = new ChatChannel(name, color, chatColor, permission, speakPermission, mutable,
					filter, defaultChannel, alias, distance, autojoin, bungee, cooldown, format);
			channels[counter++] = chatChannel;
			if (defaultChannel) {
				defaultChatChannel = chatChannel;
				defaultColor = color;
			}
		}
	}

	/**
	 * Get array of chat channels.
	 * 
	 * @return {@link ChatChannel}[]
	 */
	public static ChatChannel[] getChannels() {
		return channels;
	}

	/**
	 * Get a chat channel by name.
	 * 
	 * @param channelName
	 *            name of channel to get.
	 * @return {@link ChatChannel}
	 */
	public static ChatChannel getChannel(String channelName) {
		for (ChatChannel c : channels) {
			if (c.getName().equalsIgnoreCase(channelName) || c.getAlias().equalsIgnoreCase(channelName)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Checks if the chat channel exists.
	 * 
	 * @param channelName
	 *            name of channel to check.
	 * @return true if channel exists, false otherwise.
	 */
	public static boolean isChannel(String channelName) {
		return getChannel(channelName) != null;
	}

	/**
	 * Get default chat channel color.
	 * 
	 * @return {@link String}
	 */
	public static String getDefaultColor() {
		return defaultColor;
	}

	/**
	 * Get default chat channel.
	 * 
	 * @return {@link ChatChannel}
	 */
	public static ChatChannel getDefaultChannel() {
		return defaultChatChannel;
	}

	/**
	 * Get list of chat channels with autojoin set to true.
	 * 
	 * @return {@link List}&lt{@link ChatChannel}&gt
	 */
	public static List<ChatChannel> getAutojoinList() {
		List<ChatChannel> joinlist = new ArrayList<ChatChannel>();
		for (ChatChannel c : channels) {
			if (c.getAutojoin()) {
				joinlist.add(c);
			}
		}
		return joinlist;
	}

	/**
	 * Parameterized constructor a {@link ChatChannel}.
	 * 
	 * @param name
	 * @param color
	 * @param chatColor
	 * @param permission
	 * @param speakPermission
	 * @param mutable
	 * @param filter
	 * @param defaultChannel
	 * @param alias
	 * @param distance
	 * @param autojoin
	 * @param bungee
	 * @param cooldown
	 * @param format
	 */
	public ChatChannel(String name, String color, String chatColor, String permission, String speakPermission,
			boolean mutable, boolean filter, boolean defaultChannel, String alias, double distance, boolean autojoin,
			boolean bungee, int cooldown, String format) {
		this.name = name;
		this.color = color;
		this.chatColor = chatColor;
		this.permission = PERMISSION_PREFIX + permission;
		this.speakPermission = PERMISSION_PREFIX + speakPermission;
		this.mutable = mutable;
		this.filter = filter;
		this.defaultChannel = defaultChannel;
		this.alias = alias;
		this.distance = distance;
		this.autojoin = autojoin;
		this.bungee = bungee;
		this.cooldown = cooldown;
		this.format = format;
	}

	/**
	 * Get the name of the chat channel.
	 * 
	 * @return {@link String}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the format of the chat channel.
	 * 
	 * @return {@link String}
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Get the cooldown of the chat channel in seconds.
	 * 
	 * @return int
	 */
	public int getCooldown() {
		return cooldown;
	}

	/**
	 * Check if the chat channel is BungeeCord enabled.
	 * 
	 * @return true if the chat channel is BungeeCord enabled, false otherwise.
	 */
	public boolean getBungee() {
		return bungee;
	}

	/**
	 * Get the permissions node for the chat channel.
	 * 
	 * @return {@link String}
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * Check if autojoin is enabled for the chat channel.
	 * 
	 * @return true if autojoin is enabled, false otherwise.
	 */
	public boolean getAutojoin() {
		return autojoin;
	}

	/**
	 * Check if the chat channel allows muting.
	 * 
	 * @return true if muting is allowed, false otherwise.
	 */
	public boolean isMutable() {
		return mutable;
	}

	/**
	 * Get the formatted color of the chat channel.
	 * 
	 * @return {@link String}. Returns {@link Format#DEFAULT_COLOR_CODE} if the
	 *         color is invalid.
	 */
	public String getColor() {
		if (Format.isValidColor(color)) {
			return String.valueOf(ChatColor.valueOf(color.toUpperCase()));
		}
		if (Format.isValidHexColor(color)) {
			return Format.convertHexColorCodeToBukkitColorCode(color);
		}
		return Format.DEFAULT_COLOR_CODE;
	}

	/**
	 * Get the raw color value of the chat channel.
	 * 
	 * @return {@link String}
	 */
	public String getColorRaw() {
		return color;
	}

	/**
	 * Get the formatted chat color of the chat channel.
	 * 
	 * @return {@link String}. Returns {@link Format#DEFAULT_COLOR_CODE} if the chat
	 *         color is invalid.
	 */
	public String getChatColor() {
		if (chatColor.equalsIgnoreCase("None")) {
			return chatColor;
		}
		if (Format.isValidColor(chatColor)) {
			return String.valueOf(ChatColor.valueOf(chatColor.toUpperCase()));
		}
		if (Format.isValidHexColor(chatColor)) {
			return Format.convertHexColorCodeToBukkitColorCode(chatColor);
		}
		return Format.DEFAULT_COLOR_CODE;
	}

	/**
	 * Get the raw chat color value of the chat channel.
	 * 
	 * @return {@link String}
	 */
	public String getChatColorRaw() {
		return chatColor;
	}

	/**
	 * Check if the chat channel is the default chat channel.
	 * 
	 * @return true if the chat channel is the default chat channel, false
	 *         otherwise.
	 */
	public boolean isDefaultchannel() {
		return defaultChannel;
	}

	/**
	 * Get the alias of the chat channel.
	 * 
	 * @return {@link String}
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Get the distance of the chat channel in blocks.
	 * 
	 * @return double
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Checks if the chat channel has a distance set.
	 * 
	 * @return true if the distance is greater than zero, false otherwise.
	 */
	public boolean hasDistance() {
		return distance > 0;
	}

	/**
	 * Checks if the chat channel has a cooldown set.
	 * 
	 * @return true if the cooldown is greater than zero, false otherwise.
	 */
	public boolean hasCooldown() {
		return cooldown > 0;
	}

	/**
	 * Checks if the chat channel has a permission set.
	 * 
	 * @return true if the permission does not equal
	 *         {@link ChatChannel#NO_PERMISSIONS}, false otherwise.
	 */
	public boolean hasPermission() {
		return !permission.equalsIgnoreCase(NO_PERMISSIONS);
	}

	/**
	 * Checks if the chat channel has a speak permission set.
	 * 
	 * @return true if the speak permission does not equal
	 *         {@link ChatChannel#NO_PERMISSIONS}, false otherwise.
	 */
	public boolean hasSpeakPermission() {
		return !speakPermission.equalsIgnoreCase(NO_PERMISSIONS);
	}

	/**
	 * Get the speak permissions node for the chat channel.
	 * 
	 * @return {@link String}
	 */
	public String getSpeakPermission() {
		return speakPermission;
	}

	/**
	 * Checks if the chat channel has the filter enabled.
	 * 
	 * @return true if the chat channel has the filter enabled, false otherwise.
	 */
	public boolean isFiltered() {
		return filter;
	}

	/**
	 * Compares the chat channel by name to determine equality.
	 * 
	 * @param channel
	 *            Object to compare for equality.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object channel) {
		return channel instanceof ChatChannel && this.name.equals(((ChatChannel) channel).getName());
	}
}