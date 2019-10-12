/**
 * @author Lucas Losinskas
 * e-mail: llosinskas
 * Data outubro de 2019
 */

/*
 * Algorítimo de controle do carrinho
 */

 //=====================================================================
 // Mapeamento do Hardware
 #define echo 2   // pino echo ultrasonico 
 #define trig 3   // pino trigger ultrasonico
 #define dir1 4   // controle da direção do motor 1
 #define pwm1 5   // pwm do motor 1
 #define pwm2 6   // pwm do motor 2
 #define dir2 7   // controle da direção do motor 2 
 #define p_on 14  // Chave para a seleção de modos (PID e on-off)

 //=====================================================================
 // Configuração inicial 
 void onOff_control(float measure);    // Função para o algoritimo de controle simples (on-off)
 void pid_control(float measure);      // Função para o algoritimo PID 
 void robot_ahead();                   // Função para mover o robo para frente 
 void robot_back();                    // Função para mover o robo para trás 
 float measureDistance();              // Função para medir a distância e retornar a distância em cm 
 void trigPulse();                     // Função que gera pulso de trigger de 10us

 //=======================================================================
 // Variáveis globais
 float dist_cm;  //Armazena a distância em centímetros entre o robo e o obstáculo 
  
//======================================================================
// Configurações iniciais
void setup() {

// Difine o modo dos pinos 
pinMode(dir1, OUTPUT);        //Saída para controle de direção do motor 1
pinMode(pwm1, OUTPUT);        //Saída para o pwm do motor 1
pinMode(pwm2, OUTPUT);        //Saída para o pwm do motor 2
pinMode(dir2, OUTPUT);        //Saída para controle de direção do motor 2
pinMode(trig, OUTPUT);        //Saída para o pulso do trigger 
pinMode(echo, INPUT);         //Entrada para o pulso de echo      
pinMode(p_on, INPUT_PULLUP);  //Entrada para chave de seleção 

//Inicia os pinos 
digitalWrite(trig, LOW);                       //pino de trigger inicia em low  
digitalWrite(dir1, LOW);                       //dir1 inicia em LOW
digitalWrite(pwm1, LOW);                       //pwm1 inicia em 0
digitalWrite(pwm2, LOW);                       //pwm2 inicia em 0
digitalWrite(dir2, LOW);                       //dir2 inicia em LOW  
} //end setup 


//======================================================================
// Loop Infinito 
void loop() {

  dist_cm = measureDistance();                // Mede a distância em cm e armazena em dist_cm 

  //Passar por wifi ou serial (Implementar)

  if(digitalRead(p_on)) pid_control(dist_cm); // controle PID, se chave p_on estiver acionada 

   else onOff_control(dist_cm);               // senão controle normal

  delay(65);                                  // atualização das leituras do sensor (sugerida pelo fabricante)

} //end loop 

// =======================================================================================================
// --- Desenvolvimento das Funções ---



// =======================================================================================================
// --- Move Robô para Frente ---
// Desliga os relés da ponte H para ambos os motores
//
void robot_ahead()                                //Função para mover robô para frente
{
   digitalWrite(dir1,  LOW);
   digitalWrite(dir2,  LOW);
  
} //end robot_ahead

// =======================================================================================================
// --- Move Robô para Trás ---
// Aciona os relés da ponte H para ambos os motores
//
void robot_back()                                 //Função para mover robô para trás
{
   digitalWrite(dir1, HIGH);
   digitalWrite(dir2, HIGH);

} //end robot_ahead

// =======================================================================================================
// --- Algoritmo de controle simples (on off) ---
// Se distância inferior a 20cm, move robô para trás com pwm máximo. Senão, move robô para frente
// com pwm máximo.
//
void onOff_control(float measure)                 //Função para algoritmo de controle simples (on off)
{
   
   if(measure < 20)                               //distância medida menor que 20cm?
   {                                              //sim
      robot_back();                               //move robô para trás
      analogWrite(pwm1, 255);                     //pwm1 no máximo 
      analogWrite(pwm2, 255);                     //pwm2 no máximo
   } //end if
   
   else                                           //senão
   {                                              //distância igual ou superior a 20cm
      robot_ahead();                              //move robô para frente
      analogWrite(pwm1, 255);                     //pwm1 no máximo
      analogWrite(pwm2, 255);                     //pwm2 no máximo
   } //end else
  
} //end onOff_control

// =======================================================================================================
// --- Algoritmo Proporcional Integral Derivativo (P.I.D.) ---
// Calcula erro na medida do sensor, comparando com setpoint (20cm)
// Reduz velocidade dos motores de forma sutil até atingir 20cm
// Movimenta robô para frente (se distância superior ou igual a 20) ou para trás (se distância inferior a 20)
//

void pid_control(float measure){               // Função para o algoritimo PID

float    error_meas,                            //armazena o erro
           kp = 1.0,                              //constante kp
           ki = 0.02,                             //constante ki
           kd = 0.0,                              //constante kd
           proportional,                          //armazena valor proporcional
           integral,                              //armazena valor integral
           derivative,                            //armazena valor derivativo
           PID,                                   //armazena resultado PID
           ideal_value = 20.0,                    //valor ideal (setpoint), setado para 20cm
           lastMeasure,
           tempo,
           deltat;                           //armazena última medida

        
    error_meas = measure - ideal_value;           //calcula erro
    
    proportional = error_meas * kp;               //calcula proporcional

    deltat = millis() - tempo;
    integral += (error_meas * ki)*deltat;                  //calcula integral

    
    derivative = ((lastMeasure - measure) * kd)/deltat;    //calcula derivada ***há um erro de semântica aqui
                                                  // ex.: corrija o erro de semântica para o cálculo da derivada!
    lastMeasure = measure;                        //atualiza medida
    tempo = millis();
    PID = proportional + integral + derivative;   //calcula PID

 
    if(PID < 0)                                   //PID menor que zero?
    {                                             //sim
      PID = map(PID,  0, -20, 0, 255);            //normaliza para PWM de 8 bits
      robot_back();                               //move robô para trás
    
    } //end if PID
    
    else                                          //senão
    {                                             //PID maior ou igual a zero
      PID = map(PID,  0, 40, 0, 255);             //normaliza para PWM de 8 bits
      if(PID > 255) PID = 255;                    //se PID maior que 255, mantém em 255
      robot_ahead();                              //move robô para frente
      
    } //end else
    
    analogWrite(pwm1, PID);                       //controla PWM de acordo com o PID (motor 1)
    analogWrite(pwm2, PID);                       //controla PWM de acordo com o PID (motor 2)

    
} //end pid_control



// =======================================================================================================
// --- Mede pulso de echo e calcula distância medida pelo sensor ---
// Utiliza função pulseIn da IDE do Arduino e realiza o cálculo da distância em cm
//
float measureDistance()                           //Função que retorna a distância em centímetros
{

  float pulse;                                    //Armazena o valor de tempo em µs que o pino echo fica em nível alto
        
  trigPulse();                                    //Envia pulso de 10µs para o pino de trigger do sensor
  
  pulse = pulseIn(echo, HIGH);                    //Mede o tempo em que echo fica em nível alto e armazena em pulse
    
  /*
    >>> Cálculo da Conversão de µs para cm:
    
   Velocidade do som = 340 m/s = 34000 cm/s
   
   1 segundo = 1000000 micro segundos
   
      1000000 µs - 34000 cm/s
            X µs - 1 cm
            
                  1E6
            X = ------- = 29.41
                 34000
                 
    Para compensar o ECHO (ida e volta do ultrassom) multiplica-se por 2
    
    X' = 29.41 x 2 = 58.82
 
  */
  
  return (pulse/58.82);                           //Calcula distância em centímetros e retorna o valor
  
  
} //end measureDistante


// =======================================================================================================
// --- Gera o pulso de trigger para o acionamento do sinal de ultrassom ---
// Pulso de 10µs , conforme especificação do fabricante (vide datashet HC-SR04)
//
void trigPulse()                                  //Função para gerar o pulso de trigger para o sensor HC-SR04
{
  
   digitalWrite(trig,HIGH);                       //Saída de trigger em nível alto
   delayMicroseconds(10);                         //Por 10µs ...
   digitalWrite(trig,LOW);                        //Saída de trigger volta a nível baixo

} //end trigPulse

/*
 * Agradecimentos: 
 *  - Professor Fabio Tomm
 *  - WRKits Eng. Wagner Rambo
 *  - Brincado com ideias 
 */
