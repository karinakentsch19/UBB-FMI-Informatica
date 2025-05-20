import { Pressable, View, Text } from "react-native";
import styled from "@emotion/native";
import { useRouter } from "expo-router";
import { useExercise } from "@/hooks/exerciseProvider";

const PageView = styled(View)`background-color: #FFFFF5; flex: 1; display: flex; justify-content: center; align-items: center;`;

const MyButton = styled(Pressable)`width: 300px; height: 60px; background-color: #81B622;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 20px;`;

const CustomTextButton = styled(Text)`
    color: #000;
    font-family: inter-variable,
    font-weight: bold;
    font-size: 24;
`;

const ButtonContainer = styled(View)`
    height: 50%; 
    display: flex; 
    flex-direction: column;
    justify-content: center;
    align-items: end;
`;

const HeaderContainer = styled(View)`
    height: 50%; 
    display: flex; 
    flex-direction: column;
    justify-content: center;
    align-items: start;
`;

const HeaderText = styled(Text)`
    color: #81B622;
    font-family: inter-variable,
    font-weight: bold;
    font-size: 50;
`;

export default function StartPage() {
    const router = useRouter();

    return (
        <PageView>
            <HeaderContainer>
                <HeaderText>Math Quiz</HeaderText>
            </HeaderContainer>
            <ButtonContainer>
                <MyButton onPress={() => router.push('/QuizPage')}>
                    <CustomTextButton>Start Quiz</CustomTextButton>
                </MyButton>
            </ButtonContainer>

        </PageView>
    )
}