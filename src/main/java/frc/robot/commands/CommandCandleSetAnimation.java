package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANdle_LED;

public class CommandCandleSetAnimation extends Command {
        private CANdle_LED candle;
        private CANdle_LED.AnimationTypes animation;

        public CommandCandleSetAnimation(CANdle_LED m_candle, CANdle_LED.AnimationTypes anim) {
            addRequirements(m_candle);
            candle = m_candle;
            animation = anim;
        }

        @Override
        public void initialize() {
            candle.changeAnimation(animation);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
        
}
