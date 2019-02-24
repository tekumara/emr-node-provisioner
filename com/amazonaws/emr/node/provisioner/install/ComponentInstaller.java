package com.amazonaws.emr.node.provisioner.install;

import com.amazonaws.emr.node.provisioner.install.packaging.PackageInstaller;
import com.amazonaws.emr.node.provisioner.install.packaging.PackageResolver;
import com.amazonaws.emr.node.provisioner.install.packaging.YumPackageInstaller;
import com.amazonaws.emr.node.provisioner.util.StringUtil;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ComponentInstaller {
   private static final Logger logger = LoggerFactory.getLogger(ComponentInstaller.class);
   private static final Set<String> defaultPackages = ImmutableSet.of("emr-scripts");
   private final PackageInstaller packageInstaller;
   private final PackageResolver packageResolver;

   public ComponentInstaller(PackageResolver packageResolver) {
      this(new YumPackageInstaller(), packageResolver);
   }

   public ComponentInstaller(PackageInstaller packageInstaller, PackageResolver packageResolver) {
      this.packageInstaller = packageInstaller;
      this.packageResolver = packageResolver;
   }

   public void install(List<String> componentNames) {
      Set<String> resolvedPackages = this.packageResolver.resolve(componentNames);
      Set<String> packages = ImmutableSet.builder().addAll(defaultPackages).addAll(resolvedPackages).build();
      StopWatch watch = new StopWatch();
      watch.start();
      this.packageInstaller.install(packages);
      watch.stop();
      Duration duration = new Duration(watch.getTime());
      logger.info("Took {} to install packages:\n {}", PeriodFormat.getDefault().print(duration.toPeriod()), StringUtil.indent(packages.toString()));
   }
}
