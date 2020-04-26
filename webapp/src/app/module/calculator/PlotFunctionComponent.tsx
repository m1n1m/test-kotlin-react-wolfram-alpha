import React from "react";
import {observer} from "mobx-react";
import {computed} from "mobx";

export type PlotFunctionComponentProps = {
    plotWidth: number;
    plotHeight: number;
    plotScale: number;
    data?: number[][] | null | undefined;
}

@observer
export default class PlotFunctionComponent extends React.Component<PlotFunctionComponentProps> {

    render() {

        const polylinePoints = this.getSvgPolylinePoints();

        return (
            <svg xmlns="http://www.w3.org/2000/svg" version="1.1"
                 width={this.props.plotWidth}
                 height={this.props.plotHeight}
                 style={{border: '1px solid lightsteelblue'}}
            >
                {this.buildGrid()}
                <line x1="0" y1={this.props.plotWidth / 2} x2={this.props.plotWidth} y2={this.props.plotWidth / 2} stroke="black" />
                <line x1={this.props.plotHeight / 2} y1='0' x2={this.props.plotWidth / 2} y2={this.props.plotHeight} stroke="black" />
                <polyline stroke="blue" fill="transparent" strokeWidth={2} points={polylinePoints} />
            </svg>
        );
    }


    buildGrid() {
        let grid = [];
        const plotHeight = this.props.plotHeight;
        const plotWidth = this.props.plotWidth;
        const scale = this.props.plotScale;
        for (let i = 0; i < plotWidth / scale; i++) {
            grid.push(<line x1={i * scale} x2={i * scale} y1={0} y2={plotHeight} stroke="#87CEEB" />)
        }
        for (let i = 0; i < plotHeight / scale; i++) {
            grid.push(<line x1={0} x2={plotWidth} y1={i * scale} y2={i * scale} stroke="#87CEEB" />)
        }
        return grid
    }

    @computed
    get wCenter(): number {
        return this.props.plotWidth / 2 / this.props.plotScale;
    }

    @computed
    get hCenter(): number {
        return this.props.plotHeight / 2 / this.props.plotScale;
    }

    getSvgPolylinePoints(): string {
        const scale = this.props.plotScale;
        const wCenter = this.props.plotWidth / 2 / scale;
        const hCenter = this.props.plotHeight / 2 / scale;
        const points:number [][] = [];
        this.props.data.forEach(point => {
            points.push([(point[0] + wCenter) * scale, (hCenter - point[1]) * scale])
        });
        return points.join(' ');
    }
}