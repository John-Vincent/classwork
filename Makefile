CUR=COMS486

COMS321_DIR = ./coms321/hw11/
COMS321_FILE = hw11
COMS321_MODE = pdf

COMS331_DIR = ./coms331/hw3/
COMS331_FILE = hw3
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

COMS342_DIR = ./coms342/hw10/
COMS342_FILE = hw10
COMS342_MODE = pdf

SE329_DIR = ./se329/
SE329_FILE = gantt
SE329_MODE = pdf

STAT330_DIR = ./stat330/hw1/
STAT330_FILE = hw1
STAT330_MODE = pdf

COMS352_DIR = ./coms352/hw8/
COMS352_FILE = hw8
COMS352_MODE = pdf

COMS435_DIR = ./coms435/
COMS435_FILE = notes
COMS435_MODE = pdf

COMS417_DIR = ./coms417/hw2/java/
COMS417_FILE = $(addprefix bin/, DataDrivenMinTest Min )
COMS417_MODE = class

COMS491_DIR = ./coms491/reflection2/
COMS491_FILE = graph
COMS491_MODE = pdf

COMS486_DIR = ./coms486/hw5/
COMS486_FILE = hw5
COMS486_MODE = pdf

COMS540_DIR = ./coms540/hw1/
COMS540_FILE = hw1
COMS540_MODE = pdf

COMS574_DIR = ./coms574/hw2/
COMS574_FILE = hw2
COMS574_MODE = pdf

JAVA_DIR = ./coms486/hw1/
JAVA_FILE = $(addprefix bin/, Server Client)
JAVA_MODE = class

C_DIR = ./coms352/hw8/
C_FILE = bin/test
C_MODE = exe

default: $(addprefix $($(CUR)_DIR), $(addsuffix .$($(CUR)_MODE), $($(CUR)_FILE)))
	@echo "made $(CUR): $>"

.PHONY: default makebin clean spell run run/exe run/class

%.pdf: %.tex | makebin
	@pdflatex -interaction=nonstopmode -output-directory $($(CUR)_DIR)bin $< >> $($(CUR)_DIR)bin/latexgarbage.txt
	@rm $($(CUR)_DIR)bin/*.log $($(CUR)_DIR)bin/latexgarbage.txt

$($(CUR)_DIR)bin/%.class: $($(CUR)_DIR)%.java | makebin
	@javac -Werror -d $($(CUR)_DIR)bin/ -cp ${CLASSPATH}:$($(CUR)_DIR)bin:$($(CUR)_DIR) -Xlint $<

$($(CUR)_DIR)bin/%.exe: $($(CUR)_DIR)%.c | makebin
	@gcc -Werror -Wall -o $@ $< -lrt -lpthread

spell: $($(CUR)_DIR)$($(CUR)_FILE).tex
	@aspell -t -c $($(CUR)_DIR)$($(CUR)_FILE).tex

makebin:
	@[  -d $($(CUR)_DIR)bin ] || echo "making bin folder"
	@[  -d $($(CUR)_DIR)bin ] || mkdir $($(CUR)_DIR)bin

clean:
	@rm -r $($(CUR)_DIR)bin
	@echo "$(CUR) is now clean"

run: run/$($(CUR)_MODE)

run/exe: $($(CUR)_DIR)$($(CUR)_FILE).$($(CUR)_MODE)
	$($(CUR)_DIR)$($(CUR)_FILE).$($(CUR)_MODE)
