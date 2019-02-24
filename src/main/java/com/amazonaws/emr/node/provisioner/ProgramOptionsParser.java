package com.amazonaws.emr.node.provisioner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class ProgramOptionsParser {
   private static final String APP_NAME = "NodeProvisioner";
   private static final String NO_REPO_PROVISION_OPTION = "no-repo-provision";
   private static final String INSTALL_OPTION = "install";
   private static final String PROVISION_OPTION = "provision";
   private static final String COMPONENTS_NAMES_OPTION = "component-names";
   private final CommandLineParser parser = new DefaultParser();
   private final Options options = this.buildOptions();

   public ProgramOptionsParser() {
   }

   private Options buildOptions() {
      Options opts = new Options();
      Option provisionRepoOption = Option.builder().argName("no-repo-provision").desc("Do not provision yum .repo files").longOpt("no-repo-provision").hasArg(false).build();
      opts.addOption(provisionRepoOption);
      Option provisionOption = Option.builder().argName("provision").desc("Provision (configure and start) the components").longOpt("provision").build();
      opts.addOption(provisionOption);
      Option installOption = Option.builder().argName("install").desc(String.format("Install components. If the --%s option isn't provided, then the platform is contacted to get the component names.", "component-names")).longOpt("install").build();
      opts.addOption(installOption);
      Option componentsOption = Option.builder().argName("component-names").hasArgs().valueSeparator(',').desc("Names of the components to be installed.").longOpt("component-names").build();
      opts.addOption(componentsOption);
      return opts;
   }

   public ProgramOptions parse(String[] args) throws ParseException {
      try {
         CommandLine commandLine = this.parser.parse(this.options, args);
         boolean provisionRepo = !commandLine.hasOption("no-repo-provision");
         boolean install = commandLine.hasOption("install");
         boolean provision = commandLine.hasOption("provision");
         String[] componentsNamesArray = commandLine.getOptionValues("component-names");
         List<String> componentNames = componentsNamesArray == null ? Collections.emptyList() : Arrays.asList(componentsNamesArray);
         if (!install && !provision) {
            throw new ParseException(String.format("Either --%s or --%s should be provided", "install", "provision"));
         } else {
            return (new ProgramOptions.Builder()).withProvisionRepo(provisionRepo).withInstall(install).withProvision(provision).withComponentNames(componentNames).build();
         }
      } catch (Exception var8) {
         HelpFormatter formatter = new HelpFormatter();
         formatter.printHelp("NodeProvisioner", this.options);
         throw var8;
      }
   }
}
