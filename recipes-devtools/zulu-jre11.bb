SUMMARY = "Azul Zulu Java Development Kit (JDK) binaries"
DESCRIPTION = "This the Embedded JRE for the i686/x64 bit Intel architecture from \
 Azul Systems Inc. It is created using OpenJDK code, which is licensed under \
 GPLv2 and various other third party licenses. Azul offers a variety of \
 support plans, as well as patent indemnification and guarantees against \
 the risk of open source viral contamination, as part of its Zulu \
 Embedded commercial offerings."

PV = "11"
PV_UPDATE = "9.1"
VERSION = "11.43.55-ca"
BUILD_NUMBER = "11.0.9.1"

SUFFIX_x86-64 = "linux_x64"
SUFFIX_x86 = "linux_i686"

inherit update-alternatives

BBCLASSEXTEND = "native"

# https://cdn.azul.com/zulu/bin/zulu11.43.55-ca-jre11.0.9.1-linux_i686.tar.gz
# https://cdn.azul.com/zulu/bin/zulu11.43.55-ca-jre11.0.-linux_x64.tar.gz

# https://cdn.azul.com/zulu/bin/zulu8.50.0.51-ca-jre8.0.275-linux_i686.tar.gz

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
      d.setVarFlag("SRC_URI", "sha256sum", "90e172956246961fd56e58c1d49bbac3f972e14aa304c293880a89f91e2f9cd1")
      return
    if d.getVar("SUFFIX") == "linux_x64":
      d.setVarFlag("SRC_URI", "sha256sum", "ac64f7296cc6404e6be18a3028e4b6ee0df12d751ff21074f61cc5c829413ec3")
      return
    bb.error("Could not find remote $SUFFIX_ARCH")
}

do_install () {
  install -d -m 0755 ${D}${datadir}/zulu-${PV}_${PV_UPDATE}
  cp -a ${S}/zulu${VERSION}-jre${BUILD_NUMBER}-${SUFFIX}/* ${D}${datadir}/zulu-${PV}_${PV_UPDATE}/
  chown -R root:root ${D}${datadir}/zulu-${PV}_${PV_UPDATE}/
}
