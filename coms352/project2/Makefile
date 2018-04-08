C_COMP = gcc
C_FLAGS = -Werror -Wall -ggdb
C_LINKS = -lrt -lpthread
SERVER_FILES = $(addprefix bin/objects/, $(addsuffix .o, server))
SERVER_HEADERS = $(addprefix headers/, $(addsuffix .h, ))
CLIENT_FILES = $(addprefix bin/objects/, $(addsuffix .o, client utils))
CLIENT_HEADERS = $(addprefix headers/, $(addsuffix .h, utils))

SERVER = bin/server
CLIENT = bin/client

.PHONY: default makebin clean run headers client server

default: $(SERVER) $(CLIENT)
	@echo "making client and server"

client: $(CLIENT)
	@echo "making client"

server: $(SERVER)
	@echo "making server"

$(SERVER): $(SERVER_FILES) | makebin
	@$(C_COMP) $(C_FLAGS) -o $(SERVER) $(SERVER_FILES) $(C_LINKS)

$(CLIENT): $(CLIENT_FILES) | makebin
	@$(C_COMP) $(C_FLAGS) -o $(CLIENT) $(CLIENT_FILES) $(C_LINKS)

bin/objects/%.o: src/%.c | makebin
	@$(C_COMP) $(C_FLAGS) -c -o $@ $<

clean:
	@[ -d bin ] && echo "cleaning bin folder" && rm -r bin

makebin:
	@[ -d bin ] || echo "making bin folder"
	@[ -d bin ] || mkdir -p bin/objects

run: $(EXE)
	@$(EXE)
