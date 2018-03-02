C_COMP = gcc
C_FLAGS = -Werror -Wall
C_LINKS = -lrt -lpthread
C_FILES = $(addprefix bin/objects/, $(addsuffix .o, main input executor))
C_HEADERS = $(addprefix src/headers/, $(addsuffix .h, command const executor input))

EXE = bin/osh

.PHONY: default makebin clean run headers

default: $(EXE)

$(EXE): $(C_FILES) | makebin
	@$(C_COMP) $(C_FLAGS) -o $(EXE) $(C_FILES) $(C_LINKS) && echo "making osh"


bin/objects/%.o: src/%.c | makebin
	@$(C_COMP) $(C_FLAGS) -c -o $@ $<

clean:
	@[ -d bin ] && echo "cleaning bin folder" && rm -r bin

makebin:
	@[ -d bin ] || echo "making bin folder"
	@[ -d bin ] || mkdir -p bin/objects

run: $(EXE)
	@$(EXE)
