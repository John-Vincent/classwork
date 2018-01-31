CUR = COMS352

COMS321_DIR = ./coms321/hw11/
COMS321_FILE = hw11
COMS321_MODE = pdf

COMS331_DIR = ./coms331/hw13/
COMS331_FILE = hw13
COMS331_MODE = pdf

COMS311_2_DIR = ./coms311/project1/
COMS311_2_FILE = $(addprefix bin/, BinaryST WarWithArray WarWithBST WarWithHash WarWithRollHash test)
COMS311_2_MODE = class

COMS311_3_DIR = ./coms311/project2/
COMS311_3_FILE = $(addprefix bin/, GraphProcessor WikiCrawler test DiGraph)
COMS311_3_MODE = class

COMS311_DIR = ./coms311/hw6/
COMS311_FILE = hw6
COMS311_MODE = pdf

ENG314_DIR = ./engl314/instructional/
ENG314_FILE = plane
ENG314_MODE = pdf

COMS342_DIR = ./coms342/hw1/
COMS342_FILE = hw1
COMS342_MODE = pdf

SE329_DIR = ./se329/hw1/
SE329_FILE = hw1
SE329_MODE = pdf

STAT330_DIR = ./stat330/hw1/
STAT330_FILE = hw1
STAT330_MODE = pdf

COMS352_DIR = ./coms352/hw3/
COMS352_FILE = hw3
COMS352_MODE = pdf

default: $(addprefix $($(CUR)_DIR), $(addsuffix .$($(CUR)_MODE), $($(CUR)_FILE)))
	@echo "made $(CUR)"

%.pdf: %.tex
	@pdflatex -interaction=nonstopmode -output-directory $($(CUR)_DIR) $< >> $($(CUR)_DIR)latexgarbage.txt
	@rm $($(CUR)_DIR)*.log $($(CUR)_DIR)latexgarbage.txt

$($(CUR)_DIR)bin/%.class: makebin $($(CUR)_DIR)%.java
	@javac -Werror -d $($(CUR)_DIR)/bin/ -cp $($(CUR)_DIR)/bin:$($(CUR)_DIR) -Xlint $(word 2,$^)

spell: $($(CUR)_DIR)$($(CUR)_FILE).$($(CUR)_MODE)
	@aspell -t -c $($(CUR)_DIR)$($(CUR)_FILE).tex

makebin:
	@[  -d $($(CUR)_DIR)/bin ] || echo "making bin folder"
	@[  -d $($(CUR)_DIR)/bin ] || mkdir $($(CUR)_DIR)/bin

clean: clean/$($(CUR)_MODE)
	@echo "$(CUR) is now clean"

clean/class:
	@rm -r $($(CUR)_DIR)/bin

clean/pdf:
	@rm $($(CUR)_DIR)$($(CUR)_FILE).{pdf,log,aux} $($(CUR)_DIR)latexgarbage.txt

.PHONY: default makebin clean clean/class clean/pdf spell
