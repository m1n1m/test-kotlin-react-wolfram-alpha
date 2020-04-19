import React from "react";
import {observer} from "mobx-react";
import {Button, Checkbox, FormControlLabel, Grid, Paper, TextField, Typography} from "@material-ui/core";
import CalculationResultDto from "./CalculationResultDto";
import {action, observable, reaction} from "mobx";
import {restServices} from "../../services";

type CalculatorComponentProps = {
    classes: any;
};

@observer
export default class CalculatorComponent extends React.Component<CalculatorComponentProps> {

    @observable private result?: CalculationResultDto | null = null;

    @observable private wolframResult?: CalculationResultDto | null = null;

    @observable
    private expression?: string | null = null;

    @observable
    private callWolfram = false;

    componentDidMount(): void {
        reaction(
            () => this.expression,
            (expression, reaction) => {this.calculateExpression()}
        );
        reaction(
            () => this.callWolfram,
            (expression, reaction) => {this.calculateExpression()}
        );
    }

    @action
    calculateExpression() {

        restServices.calculationRestService
            .calculateExpression(this.expression)
            .then(r => {this.result = r});

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

    onRecallClick() {
        this.calculateExpression();
    }

    @action
    handleCheck(e, checked) {
        this.callWolfram = checked;
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
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <FormControlLabel
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
                        <Grid item xs={12}>
                            {this.result && this.result.error &&
                                <Typography color="error" noWrap>
                                    Error: { this.result.error }
                                </Typography>
                            }
                            {this.result && this.result.value &&
                                <Typography color="inherit" noWrap>
                                    Result: { this.result.value }
                                </Typography>
                            }
                            {this.wolframResult &&
                                <Typography color="inherit" noWrap>
                                    Wolfram Result: { this.wolframResult.value }
                                </Typography>
                            }
                        </Grid>
                        <Grid item xs={12}>
                        </Grid>
                        <Button variant="contained" color="primary" onClick={this.onRecallClick.bind(this)}>
                            Recall
                        </Button>
                    </Grid>
                </React.Fragment>
            </Paper>
        )
    }
}