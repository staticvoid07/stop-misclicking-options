# Stop Misclicking Options

A RuneLite plugin that stops you from misclicking menu options. Add the options you never want to click by accident, and clicking them will do nothing — like Stop Misclicking Tiles, but for any menu option.

## Installation

Install **Stop Misclicking Options** from the RuneLite Plugin Hub.

## Configuration

### Blocked options

One entry per line, either `option` or `option,target`:

- `*` is a wildcard and works in both the option and the target
- Matching is case-insensitive
- Lines starting with `//` are comments
- Leaving out the target blocks the option on everything

```
// block walking everywhere
Walk here
// don't attack any guards
Attack,Guard*
// never drop potions
Drop,*potion*
// block every option on the bank booth
*,Bank booth
```

Colour codes and combat levels are ignored when matching targets, so `Attack,Goblin` matches `Goblin (level-2)`. You can still match the level explicitly, e.g. `Attack,*level-2)`.

### Allow from right-click menu

Off by default. When enabled, only left-clicks are blocked, so you can still deliberately pick a blocked option from an open right-click menu.

## License

BSD 2-Clause, see [LICENSE](LICENSE).
