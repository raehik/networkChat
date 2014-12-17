networkChat protocol specification
==================================

The networkChat protocol allows for simple Internet chat between
clients, using a server which all clients communicate with. It copies a
bunch of syntax from IRC but skips out on all security stuff (for now).


Commands
--------

### Server commands

#### USER

Specify information about a new user (sent by new connections).


#### NICK

Set/change user nickname.


#### JOIN

Join a channel.

    
#### PING

Check that client is still there.


#### PONG

Respond to PING.


#### PRIVMSG

Send a message to the chatroom.


### Custom commands (actually a PRIVMSG)
