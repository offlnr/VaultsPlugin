# VaultsPlugin

> A lightweight personal vault plugin for Minecraft servers — no database required.

![Minecraft](https://img.shields.io/badge/Minecraft-1.21-brightgreen)
![Version](https://img.shields.io/badge/version-1.0-blue)
![License](https://img.shields.io/badge/license-MIT-lightgrey)
![Author](https://img.shields.io/badge/author-offlnr-orange)

---

## What is it?

VaultsPlugin gives every player a **personal, persistent inventory** that only they can access. Open it with `/vault` or `/caja`, store your items, and close it — everything is saved automatically. Perfect for survival, RPG, or PvP servers.

---

## Features

- **Per-player vaults** stored by UUID — no shared access, no conflicts
- **Ender chest sounds** on open and close for immersion
- **Fully configurable** title (supports color codes and `{player}` placeholder) and size
- **Zero setup** — flat-file YAML storage, works out of the box
- **Console-safe** — custom message when a non-player tries to use the command

---

## Commands

| Command  | Description                  | Permission |
|----------|------------------------------|------------|
| `/vault` | Opens your personal vault    | *(none)*   |
| `/caja`  | Alias for `/vault`           | *(none)*   |

---

## Configuration

File: `plugins/VaultsPlugin/config.yml`

```yaml
vault:
  title: "Safe of §e§o{player}§r"   # Supports color codes and {player}
  size: 54                            # Must be a multiple of 9, between 9 and 54
  messageConsole: "You can't use this command from the console!"
```

| Key              | Type    | Description                                       |
|------------------|---------|---------------------------------------------------|
| `title`          | String  | Inventory title. Use `{player}` for player name  |
| `size`           | Integer | Vault rows × 9. Valid values: 9, 18, 27, 36, 45, 54 |
| `messageConsole` | String  | Message sent when console runs the command        |

---

## Installation

1. Download the latest `.jar` from [Releases](https://github.com/offlnr/VaultsPlugin/releases).
2. Place it in your server's `plugins/` folder.
3. Restart or reload the server.
4. Edit `plugins/VaultsPlugin/config.yml` to customize and `/reload` if needed.

---

## Storage

Each player's vault is saved as a `.yml` file inside `plugins/VaultsPlugin/data/<UUID>.yml`. Inventory contents are written automatically when the vault is closed.

---

## Requirements

- Java 17+
- Paper / Spigot **1.21**

---

## Author

Made by **[offlnr](https://github.com/offlnr)**
