CUR = COMS311

COMS321_DIR = ./coms321/hw7/
COMS321_FILE = hw7
COMS321_MODE = pdf

COMS331_DIR = ./coms331/hw8/
COMS331_FILE = hw8
COMS331_MODE = pdf

COMS311_DIR = ./coms311/project1/
COMS311_FILE = $(addprefix bin/, BinaryST WarWithArray WarWithBST WarWithHash WarWithRollHash)
COMS311_MODE = class

ENG314_DIR = ./engl314/instructional/
ENG314_FILE = plane
ENG314_MODE = pdf

default: $(addprefix $($(CUR)_DIR), $(addsuffix .$($(CUR)_MODE), $($(CUR)_FILE)))
	@echo "made $(CUR)"

%.pdf: %.tex
	@pdflatex -interaction=nonstopmode -output-directory $($(CUR)_DIR) $< >> $($(CUR)_DIR)latexgarbage.txt
	@rm $($(CUR)_DIR)*.log $($(CUR)_DIR)latexgarbage.txt

$($(CUR)_DIR)bin/%.class: makebin $($(CUR)_DIR)%.java
	@javac -Werror -d $($(CUR)_DIR)/bin/ -cp $($(CUR)_DIR)/bin:$($(CUR)_DIR) -Xlint $(word 2,$^)

makebin:
	@[  -d $($(CUR)_DIR)/bin ] || echo "making bin folder"
	@[  -d $($(CUR)_DIR)/bin ] || mkdir $($(CUR)_DIR)/bin


.PHONY: default makebin
