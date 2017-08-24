DIR = ./hw0/
FILE = hw0

default: $(DIR)$(FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $(DIR) $(DIR)$(FILE).tex >> $(DIR)latexgarbage.txt
	@rm $(DIR)$(FILE).log $(DIR)latexgarbage.txt


debug: $(DIR)$(FILE).tex
	@pdflatex -interaction=nonstopmode -output-directory $(DIR) $(DIR)$(FILE).tex >> $(DIR)latexgarbage.txt
