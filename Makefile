HUSH=-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn
MVN=mvn clean
MVN_NONINTERACTIVE=mvn -B -U clean
.PHONY: clean docs test build run
SHELL:=/bin/bash

clean:
	$(MVN)

docs:
	$(MVN) site:site site:run -P deploy-ossrh

# the clean phase can be included in a single invocation
test:
	$(MVN) test

# package > test in mvn phases, so no need for test to be a prerequisite
build:
	$(MVN) package

# Something like the actual java invocation; (for the default gen'd)
# java -cp ./target/sudoku-0.0.1.jar io.github.skenvy.SudokuGUI
# (OR) java -jar ./target/sudoku-0.0.1.jar
# would be usable if it could discover the jar by name. Instead use
# https://www.mojohaus.org/exec-maven-plugin/java-mojo.html
# But this will only be relevant when the main class has main(String[] args)
run:
	$(MVN) compile exec:java

# The use of maven in GitHub Actions benefits from the batch mode options in
# $(MVN_NONINTERACTIVE) and removing the "downloading" info output via $(HUSH)
.PHONY: docs_noninteractive
docs_noninteractive:
	$(MVN_NONINTERACTIVE) site:site site:stage scm-publish:publish-scm -P deploy-ossrh -Dscmpublish.checkinComment="build based on $(SHORTSHA)" $(HUSH) -Dscmpublish.dryRun=true

.PHONY: docs_deploy
docs_deploy:
	$(MVN_NONINTERACTIVE) site:site site:stage scm-publish:publish-scm -P deploy-ossrh -Dscmpublish.checkinComment="build based on $(SHORTSHA)" $(HUSH) -Dpassword=$(PASSWORD)

.PHONY: test_noninteractive
test_noninteractive:
	$(MVN_NONINTERACTIVE) test $(HUSH)

.PHONY: build_noninteractive
build_noninteractive:
	$(MVN_NONINTERACTIVE) package $(HUSH)

.PHONY: deploy_noninteractive_ossrh
deploy_noninteractive_ossrh:
	$(MVN_NONINTERACTIVE) deploy -P deploy-ossrh,release,gpg $(HUSH)

.PHONY: deploy_noninteractive_github
deploy_noninteractive_github:
	$(MVN_NONINTERACTIVE) deploy -P deploy-github,release,gpg $(HUSH)
