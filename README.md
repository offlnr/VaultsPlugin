#VaultsPlugin

VaultsPlugin is a lightweight and configurable Minecraft plugin that gives each player a secure personal inventory, accessible through `/vault` or `/caja`. Ideal for survival, RPG, or PvP servers.

---

##Features

- Personal vaults per player (UUID-based)
- Customizable title using player names and color codes
- Inventory saved automatically on close
- Works out of the box — no database setup required

---

##Commands

| Command   | Description                 |
|-----------|-----------------------------|
| `/vault`  | Opens your personal vault   |
| `/caja`   | Alias for `/vault`          |

---

##Configuration (`config.yml`)

```yaml
vault:
  title: "Safe of §e§o{player}§r"
  size: 54
  messageConsole: "You can't use this command from the console!"
