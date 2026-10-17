package com.stopmisclickingoptions;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class StopMisclickingOptionsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(StopMisclickingOptionsPlugin.class);
		RuneLite.main(args);
	}
}
