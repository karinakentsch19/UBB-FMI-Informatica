import React, { createContext, useContext, useState, ReactNode, PropsWithChildren } from 'react';
import { create } from 'react-test-renderer';

// Define the Exercise interface
interface Exercise {
    number: number;
    question: string;
    responses: number[];
    correctAnswer: number;
    chosenAnswer: number;
}

interface ExerciseContextType { 
    exercises: Exercise[];
    chosenAnswers: number[];
    handleAnswerSelect: (response: number, currentQuestionIndex: number) => void;
}

// Create the ExerciseContext
export const ExerciseContext = createContext<ExerciseContextType>({
    exercises: [],
    chosenAnswers: [],
    handleAnswerSelect: () => {}
});

// Create the ExerciseProvider component
export const ExerciseProvider = ({ children } : PropsWithChildren) => {
    const exercises:Exercise[] = [
        {
            number: 1,
            question: "22 + 11 = ?",
            responses: [12, 33, 32, 44],
            correctAnswer: 33,
            chosenAnswer: -1
        },
        {
            number: 2,
            question: "13 + 29 = ?",
            responses: [42, 41, 32, 20],
            correctAnswer: 42,
            chosenAnswer: -1
        },
        {
            number: 3,
            question: "20 + 10 = ?",
            responses: [22, 31, 30, 40],
            correctAnswer: 30,
            chosenAnswer: -1
        },
        {
            number: 4,
            question: "45 + 55  = ?",
            responses: [99, 90, 100, 95],
            correctAnswer: 100,
            chosenAnswer: -1
        },
        {
            number: 5,
            question: "10 + 11 = ?",
            responses: [12, 23, 21, 31],
            correctAnswer: 21,
            chosenAnswer: -1
        },
        {
            number: 6,
            question: "57 + 13 = ?",
            responses: [67, 43, 60, 70],
            correctAnswer: 70,
            chosenAnswer: -1
        },
        {
            number: 7,
            question: "65 + 19 = ?",
            responses: [70, 84, 73, 74],
            correctAnswer: 84,
            chosenAnswer: -1
        },
        {
            number: 8,
            question: "29 + 11 = ?",
            responses: [31, 30, 40, 44],
            correctAnswer: 40,
            chosenAnswer: -1
        },
        {
            number: 9,
            question: "12 + 12 = ?",
            responses: [24, 34, 12, 44],
            correctAnswer: 24,
            chosenAnswer: -1
        },
        {
            number: 10,
            question: "73 + 12 = ?",
            responses: [85, 84, 75, 80],
            correctAnswer: 85,
            chosenAnswer: -1
        }
    ];

    // State for chosen answers
    const [chosenAnswers, setChosenAnswers] = useState<number[]>(Array(exercises.length).fill(-1));

    // Function to handle answer selection
    const handleAnswerSelect = (response: number, currentQuestionIndex: number) => {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= exercises.length) {
            console.error('Invalid question index');
            return;
        }
        const updatedAnswers = [...chosenAnswers];
        updatedAnswers[currentQuestionIndex] = response;
        setChosenAnswers(updatedAnswers);
    };

    // Ensure we return valid JSX
    return (
        <ExerciseContext.Provider value={{ exercises, chosenAnswers, handleAnswerSelect }}>
            {children}
        </ExerciseContext.Provider>
    );
};

// Custom hook to use the Exercise context
export const useExercise = () => {
    const context = useContext(ExerciseContext);
    if (!context) {
        throw new Error('useExercise must be used within an ExerciseProvider');
    }
    return context;
};