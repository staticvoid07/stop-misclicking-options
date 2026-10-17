package com.stopmisclickingoptions;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BlockedOptionTest
{
	@Test
	public void testParse()
	{
		List<BlockedOption> options = BlockedOption.parse("// comment\n\nWalk here\n Attack , Guard* \nDrop,\n");
		assertEquals(3, options.size());
		assertEquals("Walk here", options.get(0).getOption());
		assertNull(options.get(0).getTarget());
		assertEquals("Attack", options.get(1).getOption());
		assertEquals("Guard*", options.get(1).getTarget());
		assertNull(options.get(2).getTarget());
	}

	@Test
	public void testOptionOnly()
	{
		BlockedOption walk = new BlockedOption("walk here", null);
		assertTrue(walk.matches("Walk here", ""));
		assertTrue(walk.matches("Walk here", "<col=ffffff>Bob"));
		assertFalse(walk.matches("Walk", ""));
	}

	@Test
	public void testTargetGlob()
	{
		BlockedOption attack = new BlockedOption("Attack", "Guard*");
		assertTrue(attack.matches("Attack", "<col=ffff00>Guard<col=ff00>  (level-21)"));
		assertFalse(attack.matches("Attack", "<col=ffff00>Goblin<col=ff00>  (level-2)"));
		assertFalse(attack.matches("Talk-to", "<col=ffff00>Guard"));

		BlockedOption goblin = new BlockedOption("Attack", "goblin");
		assertTrue(goblin.matches("Attack", "<col=ffff00>Goblin<col=ff00>  (level-2)"));

		BlockedOption level = new BlockedOption("*", "*level-2)");
		assertTrue(level.matches("Attack", "<col=ffff00>Goblin<col=ff00>  (level-2)"));
	}

	@Test
	public void testItemTarget()
	{
		BlockedOption drop = new BlockedOption("Drop", "*potion*");
		assertTrue(drop.matches("Drop", "<col=ff9040>Super attack potion(4)"));
		assertFalse(drop.matches("Drink", "<col=ff9040>Super attack potion(4)"));
	}
}
