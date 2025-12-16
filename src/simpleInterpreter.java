/*
 * Copyright (c) 2025 PCazzaniga (github.com)
 *
 *     simpleInterpreter.java is part of SIMPLE.
 *
 *     SIMPLE is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SIMPLE is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SIMPLE.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class simpleInterpreter {
	public static void main(String[] args){

		List<String> argsList = new ArrayList<>(List.of(args));

		String firstArg = argsList.get(0);

		if(firstArg.equals("-h") || firstArg.equals("--help")) argsInterpreter.printHelp();
		if(firstArg.equals("-s") || firstArg.equals("--simple")) argsInterpreter.printLogo();

		if(!firstArg.endsWith(".simple")) {
			System.out.println(firstArg + " is not a valid .simple file.");
			System.exit(1);
		}

		CharStream input = null;
		try{
			input = CharStreams.fromFileName(argsList.get(0));
		} catch (IOException e) {
			System.out.println("Failed to read input file.");
			System.exit(1);
		}

		argsList.remove(0);
		argsInterpreter argsIn = new argsInterpreter(argsList);

		simpleLexer lexer = new simpleLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		simpleParser parser = new simpleParser(tokens);
		parser.removeErrorListeners();
		simpleErrorListener el = new simpleErrorListener(parser);
		parser.addErrorListener(el);
		simpleErrorStrategy handler = new simpleErrorStrategy();
		parser.setErrorHandler(handler);
		simpleParser.FileContext fileTree = parser.file();

		if (parser.getNumberOfSyntaxErrors() < 1){

			ParseTreeWalker walker = new ParseTreeWalker();

			validateListener validator = new validateListener(parser);
			walker.walk(validator, fileTree);

			if (validator.isValidationOk()){
				System.out.println("Validation successful");
				if(!argsIn.validOpts){
					System.out.println("Invalid CL call");
				} else if (argsIn.execOpt) {
					if (validator.validateProgramArgs(argsIn.programArgs.stream().map(inputHandler::typeOfLiteral).collect(Collectors.toList()))) {
						el.showCounter(false);
						executeVisitor.Builder execB = new executeVisitor.Builder(parser);
						execB.setExpectedInputs(validator.getRuntimeInputs());
						execB.setProgramArgs(argsIn.programArgs.stream().map(inputHandler::valOfLiteral).collect(Collectors.toList()));
						if(argsIn.loopLimit > 0){
							execB.setMaxLoop(argsIn.loopLimit);
						}
						if (argsIn.recLimit > 0){
							execB.setMaxRecursion(argsIn.recLimit);
						}
						executeVisitor executor = execB.build();
						executor.visitFile(fileTree);
					} else {
						System.out.println("Invalid program arguments");
					}
				}
			} else {
				System.out.println("Validation failed");
			}
		} else {
			System.out.println(parser.getNumberOfSyntaxErrors());
		}
	}

	private static class argsInterpreter{
		boolean execOpt = false;
		boolean validOpts = true;
		int loopLimit = 0;
		int recLimit = 0;
		final List<simpleRTLitParser.LiteralContext> programArgs = new ArrayList<>();

		public argsInterpreter(List<String> args) {
			List<String> argsList = new ArrayList<>(args);
			boolean ignoreRest = false;
			for (Iterator<String> itr = argsList.iterator(); itr.hasNext(); ) {
				String arg = itr.next();
				switch (arg) {
					case "-a": case "--args":
						itr.remove();
						ignoreRest = true;
						break;
					case "-e": case "--execute":
						itr.remove();
						execOpt = true;
						break;
					case "-h": case "--help":
						printHelp();
						break;
					case "-l": case "--loop":
						itr.remove();
						if (itr.hasNext()) {
							try{
								loopLimit = Integer.parseInt(itr.next());
								if(loopLimit < 1){
									validOpts = false;
									ignoreRest = true;
								}
							} catch (NumberFormatException e) {
								validOpts = false;
								ignoreRest = true;
							}
							itr.remove();
						} else {
							validOpts = false;
							ignoreRest = true;
						}
						break;
					case "-r": case "--recursion":
						itr.remove();
						if (itr.hasNext()) {
							try{
								recLimit = Integer.parseInt(itr.next());
								if(recLimit < 1){
									validOpts = false;
									ignoreRest = true;
								}
							} catch (NumberFormatException e) {
								validOpts = false;
								ignoreRest = true;
							}
							itr.remove();
						} else {
							validOpts = false;
							ignoreRest = true;
						}
						break;
					case "-s": case "--simple":
						printLogo();
						break;
					default:
						System.out.println("Unknown option " + arg + ". Use --help to view all options.");
						System.exit(1);
				}
				if (ignoreRest) break;
			}
			if (validOpts && !argsList.isEmpty()) {
				simpleRTLitLexer argsLxr = new simpleRTLitLexer(null);
				simpleRTLitParser argsPsr = new simpleRTLitParser(null);
				for (String arg : argsList) {
					argsLxr.setInputStream(CharStreams.fromString(arg));
					argsPsr.setInputStream(new CommonTokenStream(argsLxr));
					programArgs.add(argsPsr.literal());
				}
			}
		}

		private static void printLogo() {
			String logo =
					"""
					\t  _____
					\t / ____|_		  _
					\t| (___ (_)_ __ ___  _ __ | | ___
					\t \\___ \\| | '_ ` _ \\| '_ \\| |/ _ \\
					\t ____) | | | | | | | |_) | |  __/
					\t|_____/|_|_| |_| |_| .__/|_|\\___|
					\t                   |_|
					+-------------------------------------------------+
					| Simple Is My Programming Language for Education |
					+-------------------------------------------------+
					""";
			System.out.println(logo);
			System.exit(0);
		}

		private static void printHelp(){
			String helpMsg =
					"""
					SIMPLE v1.3.2 Copyright (C) 2025 PCazzaniga (github.com)
					This program is distributed under the GNU General Public License Version 3
					
					Interpreter for the S.I.M.P.L.E. programming language, validates and optionally executes a .simple file.
					
					Usage: simplexe filename [options]
					
					Example: simplexe myProgram.simple -e -l 20 --args Hello 2 5
					
					Options:
					\t-h, --help\t\tPrint this help message instead of running the interpreter\t*
					\t-a, --args\t\tUse anything after this as program arguments
					\t-e, --execute\t\tExecute file after (successful) validation
					\t-l, --loop\t\tSet custom iteration limit for conditional loops during execution
					\t-r, --recursion\t\tSet custom recursion limit for functions during execution
					\t-s, --simple\t\tPrint a cool ASCII logo instead of running the interpreter :)\t*
					
					Options with * can be used without a filename.
					""";
			System.out.println(helpMsg);
			System.exit(0);
		}
	}
}
