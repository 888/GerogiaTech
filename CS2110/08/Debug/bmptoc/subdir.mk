################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
O_SRCS += \
../bmptoc/bmptoc.o 

C_SRCS += \
../bmptoc/bmptoc.c 

OBJS += \
./bmptoc/bmptoc.o 

C_DEPS += \
./bmptoc/bmptoc.d 


# Each subdirectory must supply rules for building sources it contributes
bmptoc/%.o: ../bmptoc/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


