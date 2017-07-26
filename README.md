# battletank
Small code contest framework for battling AI tanks

Work in progress. Todos and ideas:
* Timer for rendering
* Buffered rendering
* Multiple tanks:
    * Different colors
    * Collision detection between tanks
    * Interface showing all tanks and wins
* Balance tank properties
* Shell hitting a tank destroys it
* API using sockets to control tank
    * Buffering moves
    * Maximum response time for AI
* Example Java client tank
* Example Ruby client tank
* Some simple built-in AIs

# Compile
*nix
###
    ./build.sh

Windows
###
    build.bat

# Run
###
    cd build
    java battletank/Battletank

Or use your IDE.
