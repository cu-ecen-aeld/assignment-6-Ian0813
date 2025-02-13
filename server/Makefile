CC ?= $(CROSS_COMPILE)gcc
CUR_PATH ?= $(dir $(abspath $(MAKEFILE_LIST)))
CFLAGS ?= -g -Wall -Werror
#-fsanitize=address,leak
LDFLAGS ?=
LDLIBS ?= -lm -lpthread -lrt
TARGET ?= aesdsocket
#-lfile_manipulate -lsig_func
SRC=$(wildcard $(CUR_PATH)/*.c)
OBJS=$(SRC:.c=.o)

.PHONY: all $(TARGET) clean

.DEFAULT_GOAL: all

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^ $(LDLIBS)

clean:
	$(RM) aesdsocket $(wildcard *.o) 
