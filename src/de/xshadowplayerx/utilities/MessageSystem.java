package de.xshadowplayerx.utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xshadowplayerx.easywarps.main;

public class MessageSystem {

	private static main plugin;
	private static String primaryLanguage;
	private static String fallbackLanguage;
	private String Prefix;
	private static String translationFilePath = "plugins/EasyWarps/translations/";
	private static int loggingLevel = 0; // 0 = Debug | 1 = Warning | 2 = Information
	private static HashMap<String, String> primaryTranslations;
	private static HashMap<String, String> fallbackTranslations;

	public MessageSystem(main instance) {
		plugin = instance;
		Prefix = plugin.getConfig().getString("Prefix");
		createDefaultTranslationsFile();
		updateTranslations();
	}

	private void createDefaultTranslationsFile() {
		File germanTranslationFile = new File(translationFilePath + "de-DE.yml");
		if (germanTranslationFile.exists() == false) {
			sendMessageToConsole(2,
					"No default translation file of the language \"de-DE\" found, it will be created...");
			YamlConfiguration yaml = new YamlConfiguration();
			yaml.createSection("de-DE");
			HashMap<String, String> translations = new HashMap<String, String>();
			translations.put("test1", "Test1");
			translations.put("test2", "Test2");
			translations.put("test3", "Test3");
			yaml.set("de-DE", translations);
			try {
				yaml.save(translationFilePath + "de-DE.yml");
			} catch (IOException e) {
				sendMessageToConsole(1,
						"The default translation file of the language \"de-DE\" cant created." + e.getMessage());
				e.printStackTrace();
			}
		} else {
			sendMessageToConsole(2, "The default translation file of the language \"de-DE\" found.");
		}
	}

	private static void updateTranslations() {
		primaryLanguage = plugin.getConfig().getString("Language");
		fallbackLanguage = plugin.getConfig().getString("FallbackLanguage");
		primaryTranslations = new HashMap<String, String>();
		fallbackTranslations = new HashMap<String, String>();

		File primaryLanguageFile = new File(translationFilePath + primaryLanguage + ".yml");
		YamlConfiguration primaryLanguageYaml = new YamlConfiguration();
		try {
			primaryLanguageYaml.load(primaryLanguageFile);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			Set<String> keyList = primaryLanguageYaml.getConfigurationSection(primaryLanguage).getKeys(false);

			for (int i = 0; i < keyList.toArray().length; i++) {
				String key = (String) keyList.toArray()[i];
				String value = primaryLanguageYaml.getString(primaryLanguage + "." + key);
				hashMap.put(key, value);
				sendMessageToConsole(0, "Added \"" + key + " to primary");
			}

			primaryTranslations = hashMap;
			sendMessageToConsole(2, "The following language (primary) \"" + primaryLanguage + "\" was loaded with "
					+ primaryTranslations.size() + " entries");

		} catch (IOException | InvalidConfigurationException e) {
			sendMessageToConsole(1,
					"The following language \"" + primaryLanguage + "\" could not be loaded." + e.getMessage());
			e.printStackTrace();
		}

		File fallbackLanguageFile = new File(translationFilePath + fallbackLanguage + ".yml");
		YamlConfiguration fallbackLanguageYaml = new YamlConfiguration();
		try {
			fallbackLanguageYaml.load(fallbackLanguageFile);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			Set<String> keyList = fallbackLanguageYaml.getConfigurationSection(fallbackLanguage).getKeys(false);

			for (int i = 0; i < keyList.toArray().length; i++) {
				String key = (String) keyList.toArray()[i];
				String value = fallbackLanguageYaml.getString(fallbackLanguage + "." + key);
				hashMap.put(key, value);
				sendMessageToConsole(0, "Added \"" + key + " to fallback");
			}

			fallbackTranslations = hashMap;
			sendMessageToConsole(2, "The following language (fallback) \"" + fallbackLanguage + "\" was loaded with "
					+ fallbackTranslations.size() + " entries");

		} catch (IOException | InvalidConfigurationException e) {
			sendMessageToConsole(1,
					"The following language \"" + fallbackLanguage + "\" could not be loaded." + e.getMessage());
			e.printStackTrace();
		}

	}

	private static String getLoggerLevel(int level) {
		switch (level) {
		case 0:
			return "Debug";
		case 1:
			return "Warn";
		case 2:
			return "Info";
		default:
			return "Undefined";
		}
	}

	private String getMessageFromTranslationKey(String key) {
		if (primaryTranslations.containsKey(key)) {
			return primaryTranslations.get(key);
		} else if (fallbackTranslations.containsKey(key)) {
			sendMessageToConsole(1, "The following message \"" + key
					+ "\" could not be found use fallback. Please update the translation \"" + primaryLanguage + "\".");
			return fallbackTranslations.get(key);
		} else {
			sendMessageToConsole(1,
					"The following message \"" + key + "\" could not be found. Please update the translations.");
			return key;
		}
	}

	/**
	 * Send a Message to a Player(and Console as Debug)
	 * 
	 * @param player         the player how should receive the message
	 * @param translationKey the key of the translation which should be in the file
	 */
	public void sendMessageToPlayer(Player player, String translationKey) {
		String message = getMessageFromTranslationKey(translationKey);
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + message));
		sendMessageToConsole(0, "The following message was sent to " + player.getDisplayName() + "{"
				+ player.getUniqueId() + "}: " + message);
	}

	/**
	 * Send a Message to a Player(and Console as Debug) with replaceable arguments
	 * 
	 * @param player         the player how should receive the message
	 * @param translationKey the key of the translation which should be in the file
	 * @param replaceArgs    a hashmap witch contains the key and the value witch
	 *                       should be replaced
	 */
	public void sendMessageToPlayer(Player player, String translationKey, HashMap<String, String> replaceArgs) {
		String message = getMessageFromTranslationKey(translationKey);
		for (Map.Entry<String, String> entry : replaceArgs.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			message = message.replaceAll(key, value);
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + message));
		sendMessageToConsole(0, "The following message was sent to " + player.getDisplayName() + "{"
				+ player.getUniqueId() + "}: " + message);
	}

	/**
	 * This Function sends Messages to Console
	 * 
	 * @param messageLevel Integer with following levels: 0 = Debug | 1 = Warning |
	 *                     2 = Info
	 * @param message      string with message(not translations)
	 */
	public static void sendMessageToConsole(int messageLevel, String message) {
		String prefix = "[EasyWarps | " + getLoggerLevel(messageLevel) + "] ";
		if (loggingLevel <= messageLevel) {
			switch (messageLevel) {
			case 0:
				plugin.getLogger().info(prefix + message);
				break;
			case 1:
				plugin.getLogger().warning(prefix + message);
				break;
			case 2:
				plugin.getLogger().info(prefix + message);
			default:
				plugin.getLogger().info(prefix + message);
				break;
			}
		}
	}

}
