CURRENT = COMS321

COMS321_DIR = ./coms321/hw3/
COMS321_FILE = hw3
COMS321_MODE = pdf

COMS331_DIR = ./coms331/hw3/
COMS331_FILE = hw3
COMS321_MODE = pdf

COMS311_DIR = ./coms311/hw2/
COMS311_FILE = hw2
COMS321_MODE = pdf

ENG314_DIR = ./engl314/problem/
ENG314_FILE = problem
COMS321_MODE = pdf

default: $($(CURRENT)_DIR)$($(CURRENT)_FILE).$($(CURRENT)_MODE)

engl314: CURRENT = ENG314
engl314: $($(CURRENT)_DIR)$($(CURRENT)_FILE).$($(CURRENT)_MODE)

coms331: CURRENT = COMS331
coms331: $($(CURRENT)_DIR)$($(CURRENT)_FILE).$($(CURRENT)_MODE)

coms321: CURRENT = COMS321
coms321: $($(CURRENT)_DIR)$($(CURRENT)_FILE).$($(CURRENT)_MODE)

coms311: CURRENT = COMS311
coms311: $($(CURRENT)_DIR)$($(CURRENT)_FILE).$($(CURRENT)_MODE)

%.pdf: $($(CURRENT)_DIR)$($(CURRENT)_FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $($(CURRENT)_DIR) $($(CURRENT)_DIR)$($(CURRENT)_FILE).tex >> $($(CURRENT)_DIR)latexgarbage.txt
	@rm $($(CURRENT)_DIR)$($(CURRENT)_FILE).log $($(CURRENT)_DIR)latexgarbage.txt
	@echo "making $@"

.PHONY: default coms321 coms331 coms311 engl314
