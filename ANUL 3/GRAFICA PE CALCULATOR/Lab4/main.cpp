#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <iostream>
#include <fstream>
#include <string>

void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void processInput(GLFWwindow* window, float& angle);

const unsigned int SCR_WIDTH = 800;
const unsigned int SCR_HEIGHT = 600;

std::string readFile(const char* filePath) {
    std::string content;
    std::ifstream fileStream(filePath, std::ios::in);
    if (!fileStream.is_open()) {
        std::cerr << "Could not read file " << filePath << std::endl;
        return "";
    }
    std::string line;
    while (std::getline(fileStream, line)) {
        content.append(line + "\n");
    }
    fileStream.close();
    return content;
}

int main() {
    glfwInit();
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    GLFWwindow* window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "Cuburi Lumina", NULL, NULL);
    if (!window) {
        std::cout << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);

    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress)) {
        std::cout << "Failed to initialize GLAD" << std::endl;
        return -1;
    }
    glEnable(GL_DEPTH_TEST);

    GLuint vertexShader = glCreateShader(GL_VERTEX_SHADER);
    std::string shaderCode = readFile("cub.vert");
    const char* codeArray = shaderCode.c_str();
    glShaderSource(vertexShader, 1, &codeArray, NULL);
    glCompileShader(vertexShader);

    int success;
    char infoLog[512];
    glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &success);
    if (!success) {
        glGetShaderInfoLog(vertexShader, 512, NULL, infoLog);
        std::cout << "ERROR::VERTEX::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    GLuint fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
    shaderCode = readFile("basic.frag");
    codeArray = shaderCode.c_str();
    glShaderSource(fragmentShader, 1, &codeArray, NULL);
    glCompileShader(fragmentShader);

    glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &success);
    if (!success) {
        glGetShaderInfoLog(fragmentShader, 512, NULL, infoLog);
        std::cout << "ERROR::FRAGMENT::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    GLuint shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertexShader);
    glAttachShader(shaderProgram, fragmentShader);
    glLinkProgram(shaderProgram);

    glGetProgramiv(shaderProgram, GL_LINK_STATUS, &success);
    if (!success) {
        glGetProgramInfoLog(shaderProgram, 512, NULL, infoLog);
        std::cout << "ERROR::PROGRAM::LINKING_FAILED\n" << infoLog << std::endl;
    }

    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);

    float vertices[] = {
        // positions          // normals
        -1, -1, -1,  0,  0, -1,
         1, -1, -1,  0,  0, -1,
         1,  1, -1,  0,  0, -1,
         1,  1, -1,  0,  0, -1,
        -1,  1, -1,  0,  0, -1,
        -1, -1, -1,  0,  0, -1,

        -1, -1,  1,  0,  0, 1,
         1, -1,  1,  0,  0, 1,
         1,  1,  1,  0,  0, 1,
         1,  1,  1,  0,  0, 1,
        -1,  1,  1,  0,  0, 1,
        -1, -1,  1,  0,  0, 1,

        -1,  1,  1, -1,  0, 0,
        -1,  1, -1, -1,  0, 0,
        -1, -1, -1, -1,  0, 0,
        -1, -1, -1, -1,  0, 0,
        -1, -1,  1, -1,  0, 0,
        -1,  1,  1, -1,  0, 0,

         1,  1,  1, 1, 0, 0,
         1,  1, -1, 1, 0, 0,
         1, -1, -1, 1, 0, 0,
         1, -1, -1, 1, 0, 0,
         1, -1,  1, 1, 0, 0,
         1,  1,  1, 1, 0, 0,

        -1, -1, -1, 0, -1, 0,
         1, -1, -1, 0, -1, 0,
         1, -1,  1, 0, -1, 0,
         1, -1,  1, 0, -1, 0,
        -1, -1,  1, 0, -1, 0,
        -1, -1, -1, 0, -1, 0,

        -1,  1, -1, 0, 1, 0,
         1,  1, -1, 0, 1, 0,
         1,  1,  1, 0, 1, 0,
         1,  1,  1, 0, 1, 0,
        -1,  1,  1, 0, 1, 0,
        -1,  1, -1, 0, 1, 0,
    };

    unsigned int VBO, VAO;
    glGenVertexArrays(1, &VAO);
    glGenBuffers(1, &VBO);

    glBindVertexArray(VAO);
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), (void*)(3 * sizeof(float)));
    glEnableVertexAttribArray(1);

    float angle = glm::radians(90.0f);
    float radius = 30.0f;
    glm::vec3 center(0, 10, -10);
    glm::vec3 original_cam_poz(30, 30, 0);

    while (!glfwWindowShouldClose(window)) {
        processInput(window, angle);
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUseProgram(shaderProgram);

        // camera setup
        float camX = original_cam_poz.x + cos(angle) * radius;
        float camY = original_cam_poz.y + 20.0f;  // menținem un offset pentru a modifica înălțimea camerei
        float camZ = original_cam_poz.z + sin(angle) * radius;

        glm::vec3 cameraPos(camX, camY, camZ);
        glm::mat4 view = glm::lookAt(cameraPos, center, glm::vec3(0,1,0));
        glm::mat4 projection = glm::perspective(glm::radians(45.0f), (float)SCR_WIDTH/SCR_HEIGHT, 0.1f, 100.0f);

        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "view"), 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "projection"), 1, GL_FALSE, glm::value_ptr(projection));

        glm::vec3 lightPos(0, 0, 0);
        glm::vec3 lightColor(1, 1, 1);
        glUniform3fv(glGetUniformLocation(shaderProgram, "lightPos"), 1, glm::value_ptr(lightPos));
        glUniform3fv(glGetUniformLocation(shaderProgram, "lightColor"), 1, glm::value_ptr(lightColor));
        glUniform3fv(glGetUniformLocation(shaderProgram, "viewPos"), 1, glm::value_ptr(cameraPos));

        // Desenăm cubul alb (care este sursa de lumină)
//        glm::mat4 model = glm::mat4(1.0f);
//        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
//        glUniform3f(glGetUniformLocation(shaderProgram, "objectColor"), 1, 1, 1);
//        glDrawArrays(GL_TRIANGLES, 0, 36);
//
//        // Desenăm cubul albastru
//        model = glm::mat4(1.0f);
//        model = glm::translate(model, glm::vec3(0, 20, -20));
//        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
//        glUniform3f(glGetUniformLocation(shaderProgram, "objectColor"), 0, 0, 1);
//        glDrawArrays(GL_TRIANGLES, 0, 36);

glm::mat4 model = glm::mat4(1.0f); // Matrice de transformare pentru cubul alb
model = glm::translate(model, glm::vec3(0, 0, 0));
glUniform1i(glGetUniformLocation(shaderProgram, "isLightSource"), GL_TRUE);
glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
glUniform3f(glGetUniformLocation(shaderProgram, "objectColor"), 1.0f, 1.0f, 1.0f); // Alb
glDrawArrays(GL_TRIANGLES, 0, 36); // Desenăm cubul alb

// Poziționăm cubul albastru mai departe pentru a-l vedea în mod clar
model = glm::mat4(1.0f);
model = glm::translate(model, glm::vec3(0, 20, -20)); // Poziționează cubul albastru la (0, 20, -20)
model = glm::scale(model, glm::vec3(2.0f, 2.0f, 2.0f)); // Mărim cubul albastru pentru a fi mai vizibil
glUniform1i(glGetUniformLocation(shaderProgram, "isLightSource"), GL_FALSE);
glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "model"), 1, GL_FALSE, glm::value_ptr(model));
glUniform3f(glGetUniformLocation(shaderProgram, "objectColor"), 0.0f, 0.0f, 1.0f); // Albastru
glDrawArrays(GL_TRIANGLES, 0, 36);

        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    glDeleteVertexArrays(1, &VAO);
    glDeleteBuffers(1, &VBO);

    glfwTerminate();
    return 0;
}

void framebuffer_size_callback(GLFWwindow* window, int width, int height) {
    glViewport(0, 0, width, height);
}

void processInput(GLFWwindow* window, float& angle) {
    if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS)
        angle -= glm::radians(1.0f);
    if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS)
        angle += glm::radians(1.0f);
}
