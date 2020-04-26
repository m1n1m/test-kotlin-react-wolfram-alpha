import React from "react";
import {observer} from "mobx-react";
import {Button, Checkbox, FormControlLabel, Grid, Input, Paper, TextField, Typography} from "@material-ui/core";
import CalculationResultDto from "./CalculationResultDto";
import {action, observable} from "mobx";
import {restServices} from "../../services";
import PlotFunctionComponent from "./PlotFunctionComponent";
import CallWolframAlphaPlotImageResultDto from "./CallWolframAlphaPlotImageResultDto";

type CalculatorComponentProps = {
    classes: any;
};

@observer
export default class CalculatorComponent extends React.Component<CalculatorComponentProps> {

    @observable private callResult?: CalculationResultDto | null = null;
    @observable private wolframResult?: CallWolframAlphaPlotImageResultDto | null = null;
    @observable private expression?: string | null = 'x * x';
    @observable private minX?: number | null = -5;
    @observable private maxX?: number | null = 5;
    @observable private points?: number | null = 50;
    @observable private plotWidth?: number | null = 500;
    @observable private plotHeight?: number | null = 500;
    @observable private plotScale?: number | null = 25;
    @observable private callWolfram = false;

    @action
    calculateExpression() {

        restServices.calculationRestService
            .calculateExpression(this.expression, this.minX, this.maxX, this.points)
            .then(resultDto => {
                this.callResult = resultDto;
            });

        if (this.callWolfram) {
            restServices.wolframProxyRestService
                .calculateExpression(this.expression)
                .then(r => {this.wolframResult = r});
        }
    }

    @action
    onExpressionChange(event: any) {
        this.expression = event.target.value;
    }

    onCalculateClick() {
        this.calculateExpression();
    }

    @action
    handleCheck(e, checked) {
        this.callWolfram = checked;
    }

    @action
    onChangeMinX(event: any) {
        this.minX = Number(event.target.value);
    }

    @action
    onChangeMaxX(event: any) {
        this.maxX = Number(event.target.value);
    }

    @action
    onChangePoints(event: any) {
        this.points = Number(event.target.value);
    }

    render() {

        return(
            <Paper className={this.props.classes.paper}>
                <React.Fragment>
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                            <TextField
                                required
                                id="expression"
                                name="expression"
                                placeholder="Enter what you want to calculate :)"
                                fullWidth
                                onChange={this.onExpressionChange.bind(this)}
                                value={this.expression}
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <FormControlLabel
                                control={
                                    <Input style={{marginLeft: 15}}
                                           type="number"
                                           value={this.minX}
                                           onChange={this.onChangeMinX.bind(this)}
                                    />}
                                label="Min"
                                labelPlacement="start"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <FormControlLabel
                                control={
                                    <Input style={{marginLeft: 15}}
                                           type="number"
                                           value={this.maxX}
                                           onChange={this.onChangeMaxX.bind(this)}
                                    />}
                                label="Max"
                                labelPlacement="start"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <FormControlLabel
                                control={
                                    <Input style={{marginLeft: 15}}
                                           type="number"
                                           value={this.points}
                                           onChange={this.onChangePoints.bind(this)}
                                    />}
                                label="Points"
                                labelPlacement="start"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button variant="contained" color="primary" onClick={this.onCalculateClick.bind(this)}>
                                Calculate
                            </Button>
                            <FormControlLabel
                                style={{marginLeft: 20}}
                                control={
                                    <Checkbox color="secondary"
                                              name="callWolframAlpha"
                                              value="yes"
                                              checked={ this.callWolfram }
                                              onChange={ this.handleCheck.bind(this) }
                                    />
                                }
                                label="Call Wolfram Alpha"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            {this.callResult && this.callResult.error &&
                            <Typography color="error" noWrap>
                                Error: { this.callResult.error }
                            </Typography>
                            }
                            {this.callResult && this.callResult.result &&
                            <React.Fragment>
                                <Typography noWrap>
                                    Native result
                                </Typography>
                                <PlotFunctionComponent
                                    plotWidth={this.plotWidth}
                                    plotHeight={this.plotHeight}
                                    plotScale={this.plotScale}
                                    data={this.callResult.result}
                                />
                            </React.Fragment>
                            }
                        </Grid>
                        <Grid item xs={6}>
                            {this.wolframResult && this.wolframResult.success &&
                            <React.Fragment>
                                <Typography noWrap>
                                    Wolfram result
                                </Typography>
                                <img src={this.wolframResult.plotImageUrl} width={this.plotWidth} />
                            </React.Fragment>
                            }
                        </Grid>
                    </Grid>
                </React.Fragment>
            </Paper>
        )
    }
}