package mineverse.Aust1n46.chat.localization;

import mineverse.Aust1n46.chat.utilities.Format;

/**
 * Messages internal to the plugin
 */
public enum InternalMessage {
	EMPTY_STRING(""),
	VENTURECHAT_AUTHOR("&6Written by Aust1n46"),
	VENTURECHAT_VERSION("&6VentureChat Version: {version}");
	
	private final String message;
	
	InternalMessage(String message) {
        this.message = message;
    }
	
	@Override
    public String toString() {
       return Format.FormatStringAll(this.message);
    }
}