CUR = COMS321

COMS321_DIR = ./coms321/hw7/
COMS321_FILE = hw7
COMS321_MODE = pdf

COMS331_DIR = ./coms331/hw7/
COMS331_FILE = hw7
COMS331_MODE = pdf

COMS311_DIR = ./coms311/hw3/
COMS311_FILE = jvincent-HW3
COMS311_MODE = pdf

ENG314_DIR = ./engl314/instructional/
ENG314_FILE = plane
ENG314_MODE = pdf

default: $($(CUR)_DIR)$($(CUR)_FILE).$($(CUR)_MODE)
	@echo "made $<"

%.pdf: $($(CUR)_DIR)$($(CUR)_FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $($(CUR)_DIR) $($(CUR)_DIR)$($(CUR)_FILE).tex >> $($(CUR)_DIR)latexgarbage.txt
	@rm $($(CUR)_DIR)$($(CUR)_FILE).log $($(CUR)_DIR)latexgarbage.txt

.PHONY: default
