Changelog
=========

3.3.0
-----
Changes:
* Split gregtech producers and consumers into 2 block IDs. THIS WILL GET RID OFF ALL GREGTECH PRODUCERS AND CONSUMERS

3.2.2
-----
Fixes:
* Fix ratios for RF, ic2steam and ic2superheated steam.
* Fix display of steam amount.

To use new ratios, please delete the powerconverters/common.cfg config file.

3.2.1
-----
Changes:
* Change how redstone detection works. Hopefully it makes it work better.
Fixes:
* Fix IC2 consumers not working.

3.2.0
-----
Fixes:
* Fix crashes on opening GUI in SMP.
Changes:
* Re-add gregtech back in for Gregtech 5 port.
* Energy bridge '% chg' tooltip shows the type of steam stored.
* Producers and consumers will not transfer any energy when provided with a redstone signal.

3.1.1
-----
Fixes:
* Steam consumers and producers actually work.

3.1.0
------
Changes:
* Steam consumer can take multiple types of steam (steam, ic2steam, ic2superheatedsteam) if they are registered.
* Steam producer can be crafted to output different types of steam.
* Sync power values to client on login to server. Should make tooltips more accurate.
Fixes:
* Bugs that may have been caused by client-server desync.
