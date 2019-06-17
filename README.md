OpenJill
========

**THIS PROJECT IS DISCONTINUED**

OpenJill is open source implementation of Jill Trilogy game engine (Jill of the Jungle, Jill goes Underground, Jill save the Prince).
It's release under Mozilla Public License (see LICENSE file).

* Official website : http://www.openjill.org
* Last download : http://www.openjill.org/openjill/wiki/doku.php?id=openjill:download
* Google+ community : https://plus.google.com/u/0/communities/101394478288995733520
* Twitter : https://twitter.com/hashtag/OpenJill?src=hash

Sources organization
--------------------
* abstractfile                : default implementation of abstract file file
* abstractfile-api            : api of abstract file
* cfg-file                    : default implementation of cfg file
* cfg-file-api                : api of cfg file
* dma-file                    : default implementation of dma file
* dma-file-api                : api of dma file
* dma-file-extractor          : tool to extract any information about of DMA file
* jill_parent                 : jill pom parent file
* jill_root                   : jill root with pom include all module
* jn-file                     : default implementation of jn file
* jn-file-api                 : api of jn file
* jn-file-extractor           : tool to extract any information of JN or save file
* LICENSE                     : license file
* open-jill-object-background : object and background of game
* OpenJill                    : project include all dependencies and all properties file to create game
* openjill-core               : default implementation of core game
* openjill-core-api           : api of core game
* README.md                   : this file
* sha-file                    : default implementation of sha file
* sha-file-api                : api of sha file
* sha-file-edit               : deprecated
* sha-file-extractor          : tool to extract any information of sha file
* simplegame                  : simple game engine
* vcl-file                    : default implementation of vcl file
* vcl-file-api                : api of vcl file

Build game project
------------------

First, goto jill_parent folder and run "mvn clean install".
Then, goto jill_root folder, run "mvn clean install -Plib,game,bundle".

In OpenJill/targuet folder, you have "openjill-bundle-x.x.x.jar" than is the OpenJill game engine with all denpendencies.
To run it, copy jar file in original game forlder and lauchn it.

If you remove "bundle" profile, you must provide all dependencies in class path.

You can also add "dev" profile. In this case, game must be in "../jjungle" folder. This profile is use to develop from EDI.

Build tools
-----------

To build tools, goto jill_parent folder and run "mvn clean install".
Then, goto jill_root folder, run "mvn clean install -Plib,tools,bundle".

Goto in each targuet folder tools.
