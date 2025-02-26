# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
#SRC_URI = "git://git@github.com/cu-ecen-aeld/<your assignments repo>;protocol=ssh;branch=master"
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-6-Ian0813.git;protocol=https;branch=master \
           file://aesdsocket-start-stop \
          "
SRC_URI[sha256sum] = "eb6e82c6506039b7ef8fa5535f4334d02a683a42811a93455a5fa6131d338315"
SRCREV = "4b45da03c2e01932be8d61644c907e486cfec84f"
#BB_STRICT_CHECKSUM = "0"

SRCPV = "${SRCREV}"
PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
#SRCREV = "f99b82a5d4cb2a22810104f89d4126f52f4dfaba"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git"
B = "${S}/server"

# TODO: Add the aesdsocket application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
#FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${bindir}/aesdsocket ${sysconfdir}/init.d/aesdsocket-start-stop"
# TODO: customize these as necessary for any libraries you need for your application
# (and remove comment)
#TARGET_LDFLAGS += "-pthread -lrt"
TARGET_CC_ARCH += "${LDFLAGS}"
TARGET_LDFLAGS:append = " -lpthread -lrt "

# Init script of the aeadsocket as a system service
inherit update-rc.d

INITSCRIPT_PACKAGE = "${PN}"
INITSCRIPT_NAME = "aesdsocket-start-stop"

do_configure () {
	:
}

do_compile () {
    oe_runmake
}


do_install () {
    # TODO: Install your binaries/scripts here.
    # Be sure to install the target directory with install -d first
    # Yocto variables ${D} and ${S} are useful here, which you can read about at
    # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
    # and
    # https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
    # See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
    /usr/bin/install -d ${D}${bindir}
    /usr/bin/install -d ${D}${sysconfdir}/init.d
    /usr/bin/install -m 0755 ${WORKDIR}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/
    /usr/bin/install -m 0755 ${B}/aesdsocket ${D}${bindir}/
}
