import { useExercise } from "@/hooks/exerciseProvider";
import styled from "@emotion/native";
import { router } from "expo-router";
import { useState } from "react";
import { Pressable, Image } from "react-native";
import { View, Text } from "react-native";

const PageView = styled(View)`
    background-color: #81B622; 
    height: 100%;
    width: 100%;
    display: flex; 
    justify-content: center; 
    align-items: center;
    position: relative;
`;


const CustomQuestionContainer = styled(View)`
    height: 25%; 
    width: 80%;
    display: flex; 
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: #FFFFF5;
    border-radius: 20px;
    margin: 70px 0px 30px 0px;
`;

const CustomQuestionText = styled(Text)`
    color: #000;
    font-family: inter-variable,
    font-weight: bold;
    font-size: 40;
`;

const CustomAnswersContainer = styled(View)`
    height: fit-content;
    width: 80%;
    display: flex; 
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: transparent;
    border-radius: 20px;
    gap: 20px;
    margin-bottom: 30px;

`;

const CustomTextButton = styled(Text)`
    color: #000;
    font-family: inter-variable,
    font-weight: bold;
    font-size: 30;
`;


const ResponseButton = styled(Pressable)`
    width: 100%; 
    height: 50px; 
    background-color: #FFFFF5;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
`;

const ChosenResponseButton = styled(Pressable)`
    width: 100%; 
    height: 50px; 
    background-color: #FFFFF5;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
    border: 5px solid #194f09;
`;

const NavigationContainer = styled(View)`
    height: fit-content; 
    width: 80%;
    display: flex; 
    flex-direction: row;
    justify-content: space-around;
    position: relative;
    align-items: center;
    background-color: transparent;
`;

const PreviousButton = styled(Pressable)`
    width: fit-content; 
    height: fit-content; 
    margin: 0 auto;
    display: flex;
    position: absolute;
    left: 0;
    top: 0;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
`;

const NextButton = styled(Pressable)`
    width: fit-content; 
    height: fit-content; 
    margin: 0 auto;
    display: flex;
    right: 0;
    top: 0;
    position: absolute;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
`;

const QuestionNumberText = styled(Text)`
    color: #fff;
    font-family: inter-variable,
    font-weight: bold;
    font-size: 45;
    position: absolute;
    top: 28px;
    margin: 0 auto;
`;

const DoneButton = styled(Pressable)`
    width: fit-content; 
    height: fit-content; 
    margin: 0 auto;
    display: flex;
    right: 0;
    top: 0;
    position: absolute;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
`;

interface Exercise {
    number: number,
    question: string,
    responses: number[],
    correctAnswer: number,
    chosenAnswer: number
}

export default function QuizPage() {
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const {exercises, chosenAnswers, handleAnswerSelect} = useExercise();

    return (
        <PageView>
            <QuestionNumberText>
                Question {exercises[currentQuestionIndex].number}/10
            </QuestionNumberText>
            <CustomQuestionContainer>
                <CustomQuestionText>{exercises[currentQuestionIndex].question}</CustomQuestionText>
            </CustomQuestionContainer>
            <CustomAnswersContainer>
                {
                    exercises[currentQuestionIndex].responses.map((response) => (
                        <ResponseButton 
                            key={response} 
                            onPress={() => handleAnswerSelect(response, currentQuestionIndex)} 
                            style={{ 
                                borderColor: chosenAnswers[currentQuestionIndex] === response ? '#194f09' : 'transparent',
                                borderWidth: chosenAnswers[currentQuestionIndex] === response ? 5 : 0 
                            }}
                        >
                            <CustomTextButton>{response}</CustomTextButton>
                        </ResponseButton>
                    ))
                }
            </CustomAnswersContainer>
            <NavigationContainer>
                <PreviousButton onPress={() => currentQuestionIndex > 0 && setCurrentQuestionIndex(currentQuestionIndex - 1)}>
                    {currentQuestionIndex > 0 && <Image source={require("../assets/images/left-arrow.png")} style={{ width: 50, height: 50 }} />}
                </PreviousButton>
                <NextButton onPress={() => currentQuestionIndex < exercises.length - 1 && setCurrentQuestionIndex(currentQuestionIndex + 1)}>
                    {currentQuestionIndex < exercises.length - 1 && <Image source={require("../assets/images/right-arrow.png")} style={{ width: 50, height: 50 }} />}
                </NextButton>
                <DoneButton onPress={() => router.push("/FinalResultsPage")}>
                    {currentQuestionIndex === exercises.length - 1 && <Image source={require("../assets/images/check.png")} style={{ width: 50, height: 50 }} />}
                </DoneButton>
            </NavigationContainer>
        </PageView>
    );
}