package com.stopmisclickingoptions;

import com.google.inject.Provides;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Stop Misclicking Options",
	description = "Prevents misclicking menu options",
	tags = {"menu", "misclick", "options"}
)
public class StopMisclickingOptionsPlugin extends Plugin
{
	@Inject private Client client;
	@Inject private StopMisclickingOptionsConfig config;

	private List<BlockedOption> blockedOptions = Collections.emptyList();
	// Whether the right-click menu was open as of the last client tick, i.e. before the click was processed
	private boolean menuWasOpen;

	@Override
	protected void startUp()
	{
		blockedOptions = BlockedOption.parse(config.blockedOptions());
	}

	@Override
	protected void shutDown()
	{
		blockedOptions = Collections.emptyList();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("stopmisclickingoptions"))
		{
			blockedOptions = BlockedOption.parse(config.blockedOptions());
		}
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		menuWasOpen = client.isMenuOpen();
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.isConsumed() || (config.allowFromMenu() && menuWasOpen))
		{
			return;
		}

		for (BlockedOption blocked : blockedOptions)
		{
			if (blocked.matches(event.getMenuOption(), event.getMenuTarget()))
			{
				log.debug("Blocked menu option {} {}", event.getMenuOption(), event.getMenuTarget());
				event.consume();
				return;
			}
		}
	}

	@Provides
	StopMisclickingOptionsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(StopMisclickingOptionsConfig.class);
	}
}
