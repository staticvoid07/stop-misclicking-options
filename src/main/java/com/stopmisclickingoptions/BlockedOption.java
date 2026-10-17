package com.stopmisclickingoptions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.Value;
import net.runelite.client.util.Text;
import net.runelite.client.util.WildcardMatcher;

/**
 * A single "option" or "option,target" entry from the config. Both parts accept * wildcards.
 */
@Value
class BlockedOption
{
	private static final Pattern LEVEL_SUFFIX = Pattern.compile("\\s+\\((?:level|skill)-\\d+\\)$", Pattern.CASE_INSENSITIVE);

	String option;
	// null matches any target
	String target;

	boolean matches(String menuOption, String menuTarget)
	{
		if (!WildcardMatcher.matches(option, clean(menuOption)))
		{
			return false;
		}

		if (target == null)
		{
			return true;
		}

		// Allow "Attack,Goblin" to match "Goblin  (level-2)" while still letting "*level-2*" match the full text
		String cleanTarget = clean(menuTarget);
		return WildcardMatcher.matches(target, cleanTarget)
			|| WildcardMatcher.matches(target, LEVEL_SUFFIX.matcher(cleanTarget).replaceAll(""));
	}

	private static String clean(String s)
	{
		return s == null ? "" : Text.removeTags(s).replace(' ', ' ').trim();
	}

	static List<BlockedOption> parse(String config)
	{
		List<BlockedOption> result = new ArrayList<>();
		if (config == null)
		{
			return result;
		}

		for (String line : config.split("\n"))
		{
			line = line.trim();
			if (line.isEmpty() || line.startsWith("//"))
			{
				continue;
			}

			int comma = line.indexOf(',');
			String option = comma == -1 ? line : line.substring(0, comma).trim();
			String target = comma == -1 ? null : line.substring(comma + 1).trim();
			if (option.isEmpty())
			{
				option = "*";
			}
			if (target != null && target.isEmpty())
			{
				target = null;
			}

			result.add(new BlockedOption(option, target));
		}
		return result;
	}
}
