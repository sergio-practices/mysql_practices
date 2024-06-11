#
# Makefile
# Sergio Perez Felipe
# version: 0.0.1
#

VERSION =

# Check that given variables are set and all have non-empty values,
# # die with an error otherwise.
# #
# # Params:
# #   1. Variable name(s) to test.
# #   2. (optional) Error message to print.
check_defined = \
				$(strip $(foreach 1,$1, \
				$(call __check_defined,$1,$(strip $(value 2)))))
__check_defined = \
				  $(if $(value $1),, \
				  $(error Undefined $1$(if $2, ($2))))

build:
	mvn -Ddi.propertiesPath=. clean install --global-settings ./settings.xml -DskipTests

tests:
	mvn clean install --global-settings ./settings.xml -Dspring.profiles.active=dev

deploy:
	mvn  -DskipTests=true deploy  --global-settings ./settings.xml;

sonar:
	mvn sonar:sonar --global-settings ./settings.xml

release:
	$(call check_defined, VERSION)
	mvn release:prepare release:perform -Dtag=$(VERSION) -Darguments="-DskipTests" -DreleaseVersion=$(VERSION) --batch-mode --global-settings settings.xml

all:
	@echo "Makefile needs your attention"
