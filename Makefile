.PHONY: test

test:
	clj -A\:test

repl:
	clj -A\:repl

deploy: test
	clj -Spom
	mvn deploy
