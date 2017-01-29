################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
O_SRCS += \
../imagetester/crt0.o \
../imagetester/diddy.o \
../imagetester/drawimage.o \
../imagetester/main.o 

C_SRCS += \
../imagetester/diddy.c \
../imagetester/drawimage.c 

OBJS += \
./imagetester/diddy.o \
./imagetester/drawimage.o 

C_DEPS += \
./imagetester/diddy.d \
./imagetester/drawimage.d 


# Each subdirectory must supply rules for building sources it contributes
imagetester/%.o: ../imagetester/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


