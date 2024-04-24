import sys
import json

"""
    Fast Installation:
        Arguments:
            - Port to bind the server
            - Proxy IP
            - Enable Discord
            - Discord Token
            - Discord Channel ID
            - Discord Account Owner Role ID
            - Enable Chat Relay
            - Chat Relay Channel ID
        Example:
            python3 fast-config.py 25565 localhost 1 MTIwNTU2Mz.... 12055568271405... 12055568271405... 1 118629650477692....
            python3 fast-config.py 25565 localhost 1 MTIwNTU2Mz.... 12055568271405... 12055568271405... 0 null
            python3 fast-config.py 25565 localhost 0 null null null 0 null
"""

def main():
    
    args = sys.argv[1:]
    if len(args) != 8:
        print("Missing arguments, Please check the comentary in the code.")
        exit(1)

    for index in range(len(args)):
        if args[index] == "null":
            args[index] = ""
        elif args[index] == "1":
            args[index] = True
        elif args[index] == "0":
            args[index] = False

    config = {
        "server": {
            "bind": {
                "port": args[0],
            },
            "proxyIP": args[1],
        },
        "discord": {
            "enable": args[2],
            "token": args[3],
            "channelId": args[4],
            "accountOwnerRoleId": args[5],
            "chatRelay": {
                "enable": args[6],
                "channelId": args[7],
            }
        }
    }

    with open("config.json", "w") as file:
        file.write(json.dumps(config))
    exit(0)

if __name__ == "__main__":
    main()