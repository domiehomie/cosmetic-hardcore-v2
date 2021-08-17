[![Discord](https://img.shields.io/discord/834868774909378572?label=Join%20discord)](discord.gg/wvNER5fgeM) ![GitHub](https://img.shields.io/github/license/mufinlive/cosmetic-hardcore-v2) ![TeamCity build status](https://tc.mufin.live/app/rest/builds/buildType:id:CosmeticHardcoreV2_Build/statusIcon.svg)

# Cosmetic Hardcore

Cosmetic Hardcore is a fake hardcore gamemode for your players. It is most useful for SMP servers, where you want to give players an extra challenge, but if they die, you still want them to be able to play. It is also highly configurable from the config.yml.







```yaml
############################################################################
##                                                                        ##
##                   CosmeticHardcore v2.0 Config                         ##
##                                                                        ##
############################################################################

# The data type you want to store in. Available options: FILE, NBT, DATABASE.
# FILE: will store all data in a playerdata.yml file. (not recommended)
# NBT: will store all data in the players' nbt values.
# DATABASE: will store all data into a MySQL database. Provide database details in sql section. (recommended)
storagetype: "NBT"

# MySQL information. ONLY USED WHEN OPTING FOR DATABASE.
# Only put SSL to true if you know what you're doing.
sql:
  host: "localhost"
  port: 3306
  database: "minecraft"
  username: "plugin"
  password: "walrus"
  usessl: false

# The displayname of a player with hardcore status. Use %PLAYER% for the playername and '&' for color codes.
hardcorename: "&c%PLAYER%"

# The displayname of a player without hardcore status. Use %PLAYER% for the playername and '&' for color codes.
normalname: "%PLAYER%"

# The default amount of lives a player starts with. Use 1 for real hardcore experience.
defaultlives: 1
```

## Commands & permissions

`cosmetichardcore.setlives` - `/setlives <player> <lives>` - Set the lives of a player.

`cosmetichardcore.reload` - `/chrl` - Reloads the config files used.

## API

Api docs will be available soon.

