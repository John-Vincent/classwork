CUR = ENG314

COMS321_DIR = ./coms321/hw4/
COMS321_FILE = hw4
COMS321_MODE = pdf

COMS331_DIR = ./coms331/hw4/
COMS331_FILE = hw4
COMS331_MODE = pdf

COMS311_DIR = ./coms311/hw2/
COMS311_FILE = hw2
COMS311_MODE = pdf

ENG314_DIR = ./engl314/proposal/
ENG314_FILE = proposal
ENG314_MODE = pdf

default: $($(CUR)_DIR)$($(CUR)_FILE).$($(CUR)_MODE)

%.pdf: $($(CUR)_DIR)$($(CUR)_FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $($(CUR)_DIR) $($(CUR)_DIR)$($(CUR)_FILE).tex >> $($(CUR)_DIR)latexgarbage.txt
	@rm $($(CUR)_DIR)$($(CUR)_FILE).log $($(CUR)_DIR)latexgarbage.txt
	@echo "making $@"

.PHONY: default
