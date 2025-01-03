// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.Tracer;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import javax.management.loading.PrivateClassLoader;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static final CTREConfigs ctreConfigs = new CTREConfigs();
DutyCycleEncoder m_encoder = new DutyCycleEncoder(0);
  private Command m_autonomousCommand;
  private Command m_mauto2Command;
  private Command m_mauto4Command;
  private RobotContainer m_robotContainer;
  private Command m_mauto3Command;
   private Command m_mauto5Command;
  private Command m_lbautoCommand;
  Timer time1 = new Timer();
    Command autoSelected;

  // private TalonFX m_armleft = new TalonFX(4);
private TalonFX intakePivot = new TalonFX(9);
private TalonFX m_leftFlywheel = new TalonFX(16);
private TalonFX m_rightFlywheel = new TalonFX(18);
private TalonFX m_intake = new TalonFX(8);
private Joystick m_operator = new Joystick(1);
private final Joystick driver = new Joystick(0);
private final Compressor m_compressor = new Compressor(PneumaticsModuleType.CTREPCM);
// private final PneumaticHub pneumaticHub = new PneumaticHub(19);
// Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.REVPH, 15);
Solenoid m_solenoid1 = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
Solenoid m_solenoid2= new Solenoid(PneumaticsModuleType.CTREPCM, 1);
AnalogInput hub = new AnalogInput(0);

// double pressure = AnalogInput.getPressure(0);

// enableCompressorAnolog
// Solenoid m_solenoidArm2 = new Solenoid(PneumaticsModuleType.REVPH, 2);
Boolean armSolenoidFlag = true;
Boolean autoFlag = true;
// DoubleSolenoid m_solenoid = pneumaticHub.makeDoubleSolenoid(1,2);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

   //funciton time

   private void pivotIntakeDown(double angle) {
    if (m_encoder.get()<=angle){

      intakePivot.set(-.75);
    }
    else{
      intakePivot.stopMotor();
    }
   }
private void pivotIntakeUp(double angle) {
    if (m_encoder.get()>angle){
      intakePivot.set(.8);
    }
    else{
      intakePivot.stopMotor();
    }
   }
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    //m_compressor.enableAnalog(70, 120);
    m_compressor.enableDigital();
    m_intake.setNeutralMode(NeutralModeValue.Brake);
    intakePivot.setNeutralMode(NeutralModeValue.Coast);
    m_encoder.reset();
    m_lbautoCommand = m_robotContainer.m_lbautoCommand();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("air",hub.getValue());
    SmartDashboard.putNumber("arm encoder", m_encoder.get());
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
   //m_solenoidArm1.set(true);
   // m_solenoidArm2.set(true);
  }

  @Override
  public void disabledPeriodic() {
  
  }

  
  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    time1.start();                                          

    //mid rush shinengans
    m_mauto2Command = m_robotContainer.m_mauto2Command();
    m_mauto3Command = m_robotContainer.m_mauto3Command();
    
    m_mauto4Command = m_robotContainer.m_mauto4Command();
    m_mauto5Command = m_robotContainer.m_mauto5Command();
    // if (m_mauto2Command != null) {
    //   m_mauto2Command.schedule();
    // }
    m_leftFlywheel.set(-.70);
    m_rightFlywheel.set(-.70);
    autoSelected = m_robotContainer.m_chooser.getSelected();
      
  }                          

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // if (autoSelected == m_robotContainer.getAutonomousCommand()) {
      if (time1.get() >= .3 && time1.get() < .7)  {
     
        m_intake.set(.6);
      
    }if (time1.get() >= 1 && time1.get() < 1.6)  {
        m_intake.set(0);
        pivotIntakeDown(.3);
    } if (time1.get() >= 1.7 && time1.get() <2.5)  {
        intakePivot.stopMotor();
  
         m_intake.set(-.4);      
         // schedule the autonoPmous command (example)
        if (m_autonomousCommand != null) {
          m_autonomousCommand.schedule();                              
        }
    }if (time1.get() >= 2.9 && time1.get() < 3.9) {
      pivotIntakeUp(.1);  
      m_intake.stopMotor();
    }if (time1.get() >= 4.1 && time1.get() < 4.3) {
      // m_autonomousCommand.cancel();
      m_intake.set(.8);
      intakePivot.stopMotor();
    }
  
    if (time1.get() > 4.4 && time1.get() < 5.2) {
      if (m_mauto4Command != null) {
        m_mauto4Command.schedule();
      }
      pivotIntakeDown(.22);
      m_intake.stopMotor();
    }
    if (time1.get() > 5.3 && time1.get() < 5.7) {
      intakePivot.stopMotor();
      m_intake.set(-.28);
    }
    if (time1.get() > 6 && time1.get() < 7) {
      pivotIntakeUp(.1);
      m_intake.stopMotor();
    }
    if (time1.get() > 7.5 && time1.get() < 7.7){
      m_intake.set(.5);
     
    }
    if (time1.get() > 7.8 && time1.get() < 8.5) {
      //  m_mauto4Command.cancel();
      if (m_mauto5Command != null) {
        m_mauto5Command.schedule();
      }
      pivotIntakeDown(.3);
      m_intake.stopMotor();
    }
    if (time1.get() > 8.7 && time1.get() < 9.5) {
      m_intake.set(-.75);
      intakePivot.stopMotor();
    }
    if (time1.get() > 9.5 && time1.get() < 10.5) {
      pivotIntakeUp(.1);
      m_intake.set(0);
    }
    if (time1.get() > 10.5 && time1.get() < 10.7) {
      // m_mauto5Command.cancel();
      m_intake.set(.5);
    }
    if (time1.get() > 10.7 && time1.get() < 11.7) {
      if (m_mauto2Command != null) {
        m_mauto2Command.schedule();
      }
      pivotIntakeDown(.3);
      m_intake.set(0);
    }
     if (time1.get() > 11.7 && time1.get() < 12.2) {
      m_intake.set(-.42);
    }
    if (time1.get() > 13.6 && time1.get() < 14.6) {
      pivotIntakeUp(.1);
      m_mauto2Command.cancel();
      m_intake.stopMotor();
    }
    
    // }
  
   }
    
    
    //new WaitCommand(1);

    // new WaitCommand(1);
    // if (time1.get() >.5 && time1.get() < 1)  {  
    //   m_intake.set(.5);
    //   m_intake.set(.5);
    //  }     

  //}  
  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    m_leftFlywheel.stopMotor();
    m_rightFlywheel.stopMotor();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  SmartDashboard.putNumber("encoder", m_encoder.get());


   
    if (m_operator.getRawButton(XboxController.Button.kB.value)) {
      m_leftFlywheel.set(.30);
      m_rightFlywheel.set(.30);
     }

    if (m_operator.getRawButton(XboxController.Button.kY.value)) {
      m_leftFlywheel.set(-.09);
      m_rightFlywheel.set(-.20);
     }
if (m_operator.getRawButton(XboxController.Button.kA.value)) {
      m_leftFlywheel.set(-.60);
      m_rightFlywheel.set(-.60);
     }

if (m_operator.getRawButton(XboxController.Button.kRightBumper.value)) {
      m_leftFlywheel.set(-.50);
      m_rightFlywheel.set(-.50);
     }
   
     if (m_operator.getRawButton(XboxController.Button.kX.value)) {
      m_leftFlywheel.stopMotor();
      m_rightFlywheel.stopMotor();
     }
   
     if (m_operator.getRawButton(XboxController.Button.kLeftBumper.value)) {
      m_leftFlywheel.set(-.4);
      m_rightFlywheel.set(-.4);
     }
//  if (m_operator.getRawButton(XboxController.Button.kB.value)) {
//       m_leftFlywheel.set(-.90);
//       m_rightFlywheel.set(-.90);
//      }

//     if (m_operator.getRawButton(XboxController.Button.kY.value)) {
//       m_leftFlywheel.stopMotor();
//       m_rightFlywheel.stopMotor();
//      }

    //   if (m_operator.getRawButton(XboxController.Button.kB.value)) {
    //   m_solenoid1.set(true);
    //   m_solenoid2.set(true);                          
    // }
    // if (m_operator.getRawButton(XboxController.Button.kA.value)) {
    //   m_solenoid2.set(false);
    //   m_solenoid2.set(false);                          
    // }
   
                               
    if (driver.getRawButton(PS4Controller.Button.kCircle.value)) {
      m_solenoid1.set(true);
      m_solenoid2.set(true);                          
    }
    if (driver.getRawButton(PS4Controller.Button.kSquare.value)) {
      m_solenoid1.set(false);
      m_solenoid2.set(false);                          
    }

    // if (m_operator.getRawButton(XboxController.Button.kY.value)) {
    //   m_intake.set(-.5);
    // }else if (m_operator.getRawButton(XboxController.Button.kB.value)) {
    //   m_intake.set(.5);
    // } else {
    //   m_intake.stopMotor();
    // }
   
   
    if (driver.getRawAxis(PS4Controller.Axis.kL2.value) > .1) {
      m_intake.set(driver.getRawAxis(PS4Controller.Axis.kL2.value) * .2);
    } else if (driver.getRawAxis(PS4Controller.Axis.kR2.value) > .1) {
      m_intake.set(-driver.getRawAxis(PS4Controller.Axis.kR2.value) * .3);
    } else {
      m_intake.stopMotor();
    }
    if (m_operator.getRawAxis(XboxController.Axis.kLeftTrigger.value) > .1) {
      intakePivot.set(m_operator.getRawAxis(XboxController.Axis.kLeftTrigger.value) * .6);
    } else if (m_operator.getRawAxis(XboxController.Axis.kRightTrigger.value) > .1) {
      intakePivot.set(-m_operator.getRawAxis(XboxController.Axis.kRightTrigger.value) * .6);
    } else {
      intakePivot.stopMotor();
    }
     if (driver.getRawButton(PS4Controller.Button.kL1.value)) {
      m_intake.set(.35); }
       if (driver.getRawButton(PS4Controller.Button.kR1.value)) {
      m_intake.set(0); }
      // this is intake button work in progress
    // if (m_operator.getRawButton(XboxController.Button.kY.value)){
    //   if (m_encoder.get() <= .5){
    //   intakePivot.set(-.3);
    //   } 
    // }else {
    //     intakePivot.stopMotor();
    //   }
    // if (m_operator.getRawButton(XboxController.Button.kB.value)){
    //   if (m_encoder.get() >= .02){
    //   intakePivot.set(.3);
    //   }
    // } else {
    //     intakePivot.stopMotor();
    //   }
    

    // if (m_operator.getRawButton(XboxController.Button.kLeftBumper.value)) {
    //   if (m_encoder.get() >= .5){
    //     intakePivot.set(-.5);
    //   } else {
    //   intakePivot.stopMotor();
    //   } 


    // }
  


     
     
   // }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  
  }
}