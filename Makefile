COMS321_DIR = ./coms321/hw2/
COMS321_FILE = hw2

COMS331_DIR = ./coms331/hw2/
COMS331_FILE = hw2

COMS311_DIR = ./coms311/hw1/
COMS311_FILE = hw1

CURRENT = COMS331

default: $(CURRENT)

COMS311: $(COMS311_DIR)$(COMS311_FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $(COMS311_DIR) $(COMS311_DIR)$(COMS311_FILE).tex >> $(COMS311_DIR)latexgarbage.txt
	@rm $(COMS311_DIR)$(COMS311_FILE).log $(COMS311_DIR)latexgarbage.txt

COMS321: $(COMS321_DIR)$(COMS321_FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $(COMS321_DIR) $(COMS321_DIR)$(COMS321_FILE).tex >> $(COMS321_DIR)latexgarbage.txt
	@rm $(COMS321_DIR)$(COMS321_FILE).log $(COMS321_DIR)latexgarbage.txt

COMS331: $(COMS331_DIR)$(COMS331_FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $(COMS331_DIR) $(COMS331_DIR)$(COMS331_FILE).tex >> $(COMS331_DIR)latexgarbage.txt
	@rm $(COMS331_DIR)$(COMS331_FILE).log $(COMS331_DIR)latexgarbage.txt
