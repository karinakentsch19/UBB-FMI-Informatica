import { useExercise } from "@/hooks/exerciseProvider";
import styled from "@emotion/native";
import { router } from "expo-router";
import { View, Text, Pressable } from "react-native";
import { PieChart } from "react-native-chart-kit";

const PageView = styled(View)`
    background-color: #FFFFF5; 
    height: 100%;
    width: 100%;
    display: flex; 
    justify-content: center; 
    align-items: center;
    position: relative;
`;

const FinalPageText = styled(Text)`
    color: #81B622;
    font-family: inter-variable;
    font-weight: bold;
    font-size: 30px; // Add 'px' to the font size
    margin: 0 auto;
`;

const ChartContainer = styled(View)`
    background-color: transparent; 
    height: 30%;
    width: 100%;
    display: flex; 
    justify-content: center; 
    align-items: center;
`;

const ResponsesContainer = styled(View)`
    background-color: transparent; 
    height: 40%;
    width: 100%;
    display: flex; 
    justify-content: center; 
    align-items: center;
`;

const TableContainer = styled(View)`
    background-color: transparent; 
    height: 100%;
    width: 80%;
    display: flex; 
    flex-direction: column;
    justify-content: center; 
    align-items: center;
`

const Row = styled(View)`
    background-color: transparent; 
    flex: 1;
    width: 100%;
    display: flex; 
    flex-direction: row;
    justify-content: center; 
    align-items: center;
`

const QuestionCell = styled(View)`
    background-color: transparent; 
    height: 100%;
    width: 40%;
    display: flex; 
    flex-direction: row;
    justify-content: center; 
    align-items: center;
`

const AnswerCell = styled(View)`
    background-color: transparent; 
    height: 100%;
    width: 20%;
    display: flex; 
    flex-direction: row;
    justify-content: center; 
    align-items: center;
`

const CorrectAnswerCell = styled(View)`
    background-color: transparent; 
    height: 100%;
    width: 40%;
    display: flex; 
    flex-direction: row;
    justify-content: center; 
    align-items: center;
`
const MyButton = styled(Pressable)
    `width: 300px; 
    height: 60px; 
    background-color: #81B622;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-radius: 20px;`;

const ButtonContainer = styled(View)`
    height: 20%; 
    display: flex; 
    flex-direction: column;
    justify-content: center;
    align-items: end;
`;

const CustomTextButton = styled(Text)`
    color: #000;
    font-family: inter-variable,
    font-weight: bold;
    font-size: 24;
`;

export default function FinalResultsPage() {
    const { exercises, chosenAnswers, handleAnswerSelect } = useExercise();

    const correctCount = exercises.reduce((count, exercise, index) => {
        return exercise.correctAnswer === chosenAnswers[index] ? count + 1 : count;
    }, 0);

    const incorrectCount = exercises.reduce((count, exercise, index) => {
        return exercise.correctAnswer !== chosenAnswers[index] ? count + 1 : count;
    }, 0);

    const data = [
        { name: "Correct", population: correctCount, color: "#81B622", legendFontColor: "#000", legendFontSize: 15 },
        { name: "Incorrect", population: incorrectCount, color: "#C3272B", legendFontColor: "#000", legendFontSize: 15 }
    ];

    const totalPopulation = data.reduce((sum, item) => sum + item.population, 0);

    // Add percentage calculation to each item
    const dataWithPercentage = data.map(item => ({
        ...item,
        percentage: ((item.population / totalPopulation) * 100).toFixed(1) // Rounded to 1 decimal
    }));

    return (
        <PageView>
            <View>
                <FinalPageText>Summary</FinalPageText>
            </View>
            <ChartContainer>
                <PieChart
                    data={data}
                    width={200}
                    height={150}
                    chartConfig={{
                        backgroundColor: "#ffffff",
                        backgroundGradientFrom: "#ffffff",
                        backgroundGradientTo: "#ffffff",
                        color: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
                        labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
                        style: {
                            borderRadius: 16,
                            paddingLeft: "auto",
                            paddingRight: "auto"
                        },
                    }}
                    accessor="population"
                    backgroundColor="transparent"
                    paddingLeft="15"
                    center={[35, 10]}
                    hasLegend={false}
                />
                <View style={{ flexDirection: 'row', justifyContent: 'center', marginTop: 15 }}>
                    {dataWithPercentage.map((item, index) => (
                        <View key={index} style={{ flexDirection: 'row', alignItems: 'center', marginHorizontal: 10 }}>
                            <View style={{
                                width: 20,
                                height: 20,
                                backgroundColor: item.color,
                                borderRadius: 5,
                                marginRight: 1,
                            }} />
                            <Text style={{ color: item.legendFontColor }}>
                                {item.name} - {item.percentage}%
                            </Text>
                        </View>
                    ))}
                </View>
            </ChartContainer>
            <ResponsesContainer>
                <TableContainer>
                    <Row>
                        <QuestionCell>Question</QuestionCell>
                        <AnswerCell>Answer</AnswerCell>
                        <CorrectAnswerCell>Correct answer</CorrectAnswerCell>
                    </Row>
                    {
                        exercises.map((exercise, index) => {
                            return (
                                <Row>
                                    <QuestionCell>{exercise.question}</QuestionCell>
                                    <AnswerCell>{chosenAnswers[index]}</AnswerCell>
                                    <CorrectAnswerCell>{exercise.correctAnswer}</CorrectAnswerCell>
                                </Row>
                            );
                        })
                    }
                </TableContainer>
            </ResponsesContainer>
            <ButtonContainer>
                <MyButton onPress={() => router.push('/StartPage')}>
                    <CustomTextButton>Retry Quiz</CustomTextButton>
                </MyButton>
            </ButtonContainer>
        </PageView>
    );
}