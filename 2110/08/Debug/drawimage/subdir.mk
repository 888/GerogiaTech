################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../drawimage/diddy.c \
../drawimage/main.c 

OBJS += \
./drawimage/diddy.o \
./drawimage/main.o 

C_DEPS += \
./drawimage/diddy.d \
./drawimage/main.d 


# Each subdirectory must supply rules for building sources it contributes
drawimage/%.o: ../drawimage/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


