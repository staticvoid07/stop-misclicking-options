package com.stopmisclickingoptions;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("stopmisclickingoptions")
public interface StopMisclickingOptionsConfig extends Config
{
	@ConfigItem(
		keyName = "blockedOptions",
		name = "Blocked options",
		description = "One entry per line, in the form option or option,target.<br>"
			+ "Use * as a wildcard, e.g. 'Attack,Guard*' or 'Drop,*potion*'.<br>"
			+ "Matching is case-insensitive. Lines starting with // are ignored.",
		position = 0
	)
	default String blockedOptions()
	{
		return "";
	}

	@ConfigItem(
		keyName = "allowFromMenu",
		name = "Allow from right-click menu",
		description = "Only block left-clicks; picking a blocked option from an open right-click menu still works",
		position = 1
	)
	default boolean allowFromMenu()
	{
		return false;
	}
}
