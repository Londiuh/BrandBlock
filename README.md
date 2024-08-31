# BrandBlock
Simple Fabric server-side mod to block certain client brands from joining your server

## Configuration
`config/brandblock.json`
```json
{
  "blockedBrands": [                             // All brands that will be blocked
    "forge",
    "vanilla"
  ],
  "kickMsg": "This client brand is not allowed"  // The disconnection reason shown to the player, can be in Raw JSON text format
}
```

## How it works?
When joining a server, during the configuration phase, both the server and client send a [`minecraft:brand`](https://wiki.vg/Plugin_channels#minecraft:brand) [custom payload packet (aka plugin message)](https://wiki.vg/index.php?title=Protocol&oldid=18641#Serverbound_Plugin_Message_.28configuration.29) containing the brand name. For unmodified clients and servers, this brand is always "vanilla." However, custom clients (such as Forge, Fabric, etc.) typically change this brand identifier.

This server-side mod listens when a brand payload is received and checks if it matches any blocked brands specified in the configuration file. If a blocked brand is detected, the player is disconnected from the server. However, this can be easily bypassed and modified clients may not to send this packet at all.
