SUMMARY = "Azul Zulu Java Development Kit (JDK) binaries"
DESCRIPTION = "This the Embedded JRE for the i686/x64 bit Intel architecture from \
 Azul Systems Inc. It is created using OpenJDK code, which is licensed under \
 GPLv2 and various other third party licenses. Azul offers a variety of \
 support plans, as well as patent indemnification and guarantees against \
 the risk of open source viral contamination, as part of its Zulu \
 Embedded commercial offerings."

PV = "1.8.0"
PV_UPDATE = "262"
VERSION = "8.48.0.51-ca"
BUILD_NUMBER = "8.0.262"

SUFFIX_x86-64 = "linux_x64"
SUFFIX_x86 = "linux_i686"

inherit update-alternatives

BBCLASSEXTEND = "native"

LICENSE = "GPL-2.0-with-classpath-exception"
LIC_FILES_CHKSUM = "file://zulu${VERSION}-jre${BUILD_NUMBER}-${SUFFIX}/LICENSE;md5=3e0b59f8fac05c3c03d4a26bbda13f8f"
SRC_URI="https://cdn.azul.com/zulu/bin/zulu${VERSION}-jre${BUILD_NUMBER}-${SUFFIX}.tar.gz"

PR = "u${PV_UPDATE}"
S = "${WORKDIR}"

INSANE_SKIP_${PN} = "${ERROR_QA} ${WARN_QA}"
DEPENDS = "alsa-lib libxi libxrender libxtst"

PACKAGES = "${PN}"
PRIVATE_LIBS_${PN} = "libjvm.so"

FILES_${PN} = "/usr/"
RPROVIDES_${PN} = "zulu-jre java2-runtime"
PROVIDES += "virtual/java"

ALTERNATIVE_${PN} = "java"
ALTERNATIVE_LINK_NAME[java] = "${bindir}/java"
ALTERNATIVE_TARGET[java] = "${datadir}/zulu-${PV}_${PV_UPDATE}/bin/java"
ALTERNATIVE_PRIORITY[java] = "100"

do_fetch[prefuncs] += "fetch_checksums"
python fetch_checksums() {
    if d.getVar("SUFFIX") == "linux_i686":
      d.setVarFlag("SRC_URI", "sha256sum", "25c36b269fa366fa3b2cd67a4345b4814cb41dfa38537efcec4a9c72464986e4")
      return
    if d.getVar("SUFFIX") == "linux_x64":
      d.setVarFlag("SRC_URI", "sha256sum", "ecebe64f2acde5477c0856d2b57738b19a6e05726e0c7bbb696ac10484bb43bd")
      return
    bb.error("Could not find remote $SUFFIX_ARCH")
}

do_install () {
  install -d -m 0755 ${D}${datadir}/zulu-${PV}_${PV_UPDATE}
  cp -a ${S}/zulu${VERSION}-jre${BUILD_NUMBER}-${SUFFIX}/* ${D}${datadir}/zulu-${PV}_${PV_UPDATE}/
  chown -R root:root ${D}${datadir}/zulu-${PV}_${PV_UPDATE}/
}
