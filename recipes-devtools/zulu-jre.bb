PV = "1.8.0"
PV_UPDATE = "242"
VERSION = "8.44.0.11-ca"
BUILD_NUMBER = "8.0.242"
SUFFIX = "linux_x64"

SUMMARY = "Azul Zulu Java Development Kit (JDK) binaries"
DESCRIPTION = "This the Embedded JRE for the 64 bit Intel architecture from \
 Azul Systems Inc. It is created using OpenJDK code, which is licensed under \
 GPLv2 and various other third party licenses. Azul offers a variety of \
 support plans, as well as patent indemnification and guarantees against \
 the risk of open source viral contamination, as part of its Zulu \
 Embedded commercial offerings."

BBCLASSEXTEND = "native"

LICENSE = "GPL-2.0-with-classpath-exception"
LIC_FILES_CHKSUM = "file://zulu${VERSION}-jre${BUILD_NUMBER}-${SUFFIX}/LICENSE;md5=3e0b59f8fac05c3c03d4a26bbda13f8f"

SRC_URI="https://cdn.azul.com/zulu/bin/zulu${VERSION}-jre${BUILD_NUMBER}-${SUFFIX}.tar.gz"

SRC_URI[sha256sum] = "8bbac34a9f79439d212425a36f7bb6fa8dc3bfa836c57eff6d35e86af6277f65"

PR = "u${PV_UPDATE}"
S = "${WORKDIR}"

do_install () {
  install -d -m 0755 ${D}${datadir}/zulu-${PV}_${PV_UPDATE}
  cp -a ${S}/zulu${VERSION}-jre${BUILD_NUMBER}-${SUFFIX}/* ${D}${datadir}/zulu-${PV}_${PV_UPDATE}
}

INSANE_SKIP_${PN} = "${ERROR_QA} ${WARN_QA}"

DEPENDS = "alsa-lib libxi libxrender libxtst"

FILES_${PN} = "/usr/"

RPROVIDES_${PN} = "zulu-jre java2-runtime"
PROVIDES += "virtual/java"

inherit update-alternatives

ALTERNATIVE_${PN} = "java"
ALTERNATIVE_LINK_NAME[java] = "${bindir}/java"
ALTERNATIVE_TARGET[java] = "${datadir}/zulu-${PV}_${PV_UPDATE}/bin/java"
ALTERNATIVE_PRIORITY[java] = "100"