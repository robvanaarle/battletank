# battletank
Small code contest framework for battling AI tanks. Things are kept simple to have a nice code contest afternoon.

Work in progress. Todos and ideas:
* Interface to select tanks, (re)strart and stop match
* Better collision detection between tanks
* Balance tank properties
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
    - or -
    java -cp ".:../libs/gson-2.8.5.jar" battletank/Endpoint

# Docker
    docker-compose up -d novnc
    docker-compose up battletank

Open localhost:8080/vnc.html and click connect.
